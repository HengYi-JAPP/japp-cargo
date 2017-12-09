package com.hengyi.japp.cargo.application;

import com.hengyi.japp.cargo.domain.gps.CarData;
import com.hengyi.japp.cargo.domain.gps.CarGpsData;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 * Created by jzb on 16-3-29.
 */
public interface GpsDataService {
    Optional<CarGpsData> getGpsData(String carNo);

    Set<CarData> allCar() throws IOException;

    final class Urls {
        public static final String login = "http://122.227.227.216/gps/mobile/loginAction.jsp";
        public static final String allCar = "http://122.227.227.216/gps/mobile/getAllVhcWithState.jsp?jsonp";
        //加车牌号
        public static final String oneCarTpl = "http://122.227.227.216/gps/mobile/getVhcPosition.jsp?vhcCode=${carNo}";
    }
}
