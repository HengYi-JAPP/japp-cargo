package com.hengyi.japp.cargo.interfaces.gps.internal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.hengyi.japp.cargo.interfaces.gps.GpsDataService;
import com.hengyi.japp.cargo.interfaces.gps.CarData;
import com.hengyi.japp.cargo.interfaces.gps.CarGpsData;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.jzb.J;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.jzb.Constant.MAPPER;

/**
 * Created by jzb on 16-3-29.
 */
@ApplicationScoped
public class GpsDataServiceImpl implements GpsDataService {
    private static final Predicate<String> validResFun = res -> {
        res = StringUtils.deleteWhitespace(res);
        return !(StringUtils.isBlank(res) ||
                Objects.equals("([])", res) ||
                Objects.equals("(null)", res));
    };
    private static final Function<String, JsonNode> toJsonF = res -> Optional.ofNullable(res)
            .filter(validResFun)
            .map(J::deleteWhitespace)
            .map(it -> it.substring(it.indexOf("["), it.lastIndexOf("]") + 1))
            .map(it -> StringUtils.replace(it, "'", "\""))
            .map(it -> {
                try {
                    return MAPPER.readTree(it);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .orElse(null);
    private final OkHttpClient httpClient = new OkHttpClient.Builder().cookieJar(new CookieJar() {
        private List<Cookie> gpsCookies;

        @Override
        public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
            if (Objects.equals("/gps/mobile/loginAction.jsp", httpUrl.encodedPath()))
                gpsCookies = list;
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl httpUrl) {
            return gpsCookies == null ? Collections.emptyList() : gpsCookies;
        }
    }).build();
    @Inject
    private Logger log;

    private Optional<String> request(final String url) {
        Request request = new Request.Builder().url(url).build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("url[" + url + "],访问失败!");
                return Optional.empty();
            }
            try (ResponseBody body = response.body()) {
                return Optional.ofNullable(body.string());
            }
        } catch (IOException e) {
            log.error("url[" + url + "],访问失败!", e);
            return Optional.empty();
        }
    }

    private void setSession() {
        String p1 = String.join("=", "logonType", "user");
        String p2 = String.join("=", "username", "hengyiwl2");
        String p3 = String.join("=", "password", "123456");
        String p = String.join("&", p1, p2, p3);
        String url = String.join("?", Urls.login, p);
        request(url).orElseThrow(RuntimeException::new);
    }

    /**
     * gps=['PTA','15968948504','0002','定位信号[无]  通讯信号[无]','2016-04-01 15:02:07','120.407062','30.166813','120.400506','30.161053','浙江省杭州市萧山区;104国道,104国道-成虎路交叉路口;成虎路104国道口,衙前镇初级中学,衙前派出所附近','0','北','509.6','0','254218800','']
     * <p>
     * 车队：',gps[0]
     * 卡号：',gps[1]
     * 终端：',gps[2]
     * 信号：',gps[3]
     * 时间：',gps[4]
     * blng = gps[5], blat = gps[6]
     * <p>
     * addrs = gps[9].split(';');
     * 省市：',addrs[0]
     * 道路：',addrs[1]
     * 附近：',addrs[2]
     * <p>
     * 速度：',gps[10],'公里/小时'
     * 方向：',gps[11]
     * 日程：',gps[12],'公里
     */
    @Override
    public Optional<CarGpsData> getGpsData(final String carNo) {
        final String url = J.strTpl(Urls.oneCarTpl, ImmutableMap.of("carNo", carNo));

        return request(url).map(res -> {
            if (validResFun.test(res))
                return res;
            else {
                setSession();
                return request(url).orElse(null);
            }
        }).map(toJsonF).map(node -> {
            ArrayNode arrayNode = (ArrayNode) node;
            CarGpsData result = new CarGpsData();
            result.setCarNo(carNo);
            result.setDateTimeString(arrayNode.get(4).asText());
            result.setLongitude(arrayNode.get(5).asDouble());
            result.setLatitude(arrayNode.get(6).asDouble());

            String[] addrs = arrayNode.get(9).asText().split(";");
            if (addrs.length > 0)
                result.setProvince(addrs[0]);
            if (addrs.length > 1)
                result.setRoad(addrs[1]);
            if (addrs.length > 2)
                result.setNear(addrs[2]);

            result.setSpeed(arrayNode.get(10).asDouble());
            result.setDirection(arrayNode.get(11).asText());
            result.setKm(arrayNode.get(12).asDouble());
            return result;
        });
    }

    @Override
    public Set<CarData> allCar() {
        return request(Urls.allCar).map(res -> {
            if (validResFun.test(res))
                return res;
            else {
                setSession();
                return request(Urls.allCar).orElse(null);
            }
        }).map(toJsonF).map(it -> {
            ArrayNode arrayNode = (ArrayNode) it;
            Set<CarData> result = Sets.newHashSetWithExpectedSize(arrayNode.size());
            arrayNode.forEach(node -> {
                String carNo = node.get(0).asText();
                CarData carData = new CarData();
                carData.setCarNo(carNo);
                result.add(carData);
            });
            return result;
        }).orElseThrow(RuntimeException::new);
    }
}
