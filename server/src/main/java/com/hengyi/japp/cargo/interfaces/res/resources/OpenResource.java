package com.hengyi.japp.cargo.interfaces.res.resources;

import com.hengyi.japp.cargo.Util;
import com.hengyi.japp.cargo.application.command.DailyDetailExportTokenCommand;
import com.hengyi.japp.cargo.application.command.EntityDTO;
import com.hengyi.japp.cargo.application.query.MegSendInfoQuery;
import com.hengyi.japp.cargo.application.query.PtaSendInfoQuery;
import com.hengyi.japp.cargo.domain.AbstractReceiveInfoEntity;
import com.hengyi.japp.cargo.domain.AbstractSendInfoEntity;
import com.hengyi.japp.cargo.domain.config.HeadInfo;
import com.hengyi.japp.cargo.domain.config.SupplyInfo;
import com.hengyi.japp.cargo.domain.config.TransCorp;
import com.hengyi.japp.cargo.domain.meg.MegReceiveInfo;
import com.hengyi.japp.cargo.domain.meg.MegSendInfo;
import com.hengyi.japp.cargo.domain.pta.PtaReceiveInfo;
import com.hengyi.japp.cargo.domain.pta.PtaSendInfo;
import com.hengyi.japp.cargo.domain.repository.MegSendInfoRepository;
import com.hengyi.japp.cargo.domain.repository.PtaSendInfoRepository;
import com.hengyi.japp.cargo.domain.sap.Lfa1;
import com.hengyi.japp.cargo.domain.sap.T001;
import com.hengyi.japp.cargo.domain.sap.T001l;
import com.sun.security.auth.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.validator.constraints.NotBlank;
import org.jzb.J;
import org.jzb.poi.JPoi;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.net.URLEncoder;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.hengyi.japp.cargo.Constant.JAPP_CARGO_PERMANENT_KEY;
import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;
import static org.jzb.Constant.MAPPER;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("opens")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class OpenResource {
    @Inject
    private PtaSendInfoRepository ptaSendInfoRepository;
    @Inject
    private MegSendInfoRepository megSendInfoRepository;

    @Path("reports/dailyDetailExport")
    @GET
    @Produces(APPLICATION_OCTET_STREAM)
    public Response dailyDetailExport(@Valid @NotBlank @QueryParam("token") String token) throws Exception {
        final Claims claims = Jwts.parser().setSigningKey(JAPP_CARGO_PERMANENT_KEY).parseClaimsJws(token).getBody();
        final Principal principal = new UserPrincipal(claims.getIssuer());
        final DailyDetailExportTokenCommand command = MAPPER.convertValue(claims.get("command"), DailyDetailExportTokenCommand.class);
        final LocalDate ldStart = J.localDate(command.getStartDate());
        final LocalDate ldEnd = J.localDate(command.getEndDate());
        final Collection<String> bukrses = J.emptyIfNull(command.getT001s())
                .stream()
                .map(EntityDTO::getId)
                .collect(Collectors.toSet());
        PtaSendInfoQuery ptaQuery = new PtaSendInfoQuery(principal);
        ptaQuery.ldStart = ldStart;
        ptaQuery.ldEnd = ldEnd;
        ptaSendInfoRepository.query(ptaQuery);
        MegSendInfoQuery megQuery = new MegSendInfoQuery(principal);
        megQuery.ldStart = ldStart;
        megQuery.ldEnd = ldEnd;
        megSendInfoRepository.query(megQuery);
        final XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("PTA");
        fillPtaData(sheet, ptaQuery.result.filter(sendInfo -> {
            if (command.isAllT001s()) {
                return true;
            }
            return Optional.ofNullable(sendInfo)
                    .map(PtaSendInfo::getReceiveInfo)
                    .map(PtaReceiveInfo::getT001)
                    .map(T001::getBukrs)
                    .map(bukrses::contains)
                    .orElse(false);
        }));
        sheet = wb.createSheet("MEG");
        fillMegData(sheet, megQuery.result.filter(sendInfo -> {
            if (command.isAllT001s()) {
                return true;
            }
            return Optional.ofNullable(sendInfo)
                    .map(MegSendInfo::getReceiveInfo)
                    .map(MegReceiveInfo::getT001)
                    .map(T001::getBukrs)
                    .map(bukrses::contains)
                    .orElse(false);
        }));
        StreamingOutput result = (out) -> wb.write(out);
        String encodeDownloadName = URLEncoder.encode("导出.xlsx", UTF_8.name());
        return Response.ok(result)
                .header("Content-Disposition", "attachment;filename=" + encodeDownloadName)
                .build();
    }

    private void fillPtaData(XSSFSheet sheet, Stream<PtaSendInfo> sendInfos) {
        fillHeadData(sheet);
        AtomicInteger rowAtomicInteger = new AtomicInteger();
        sendInfos.forEach(sendInfo -> {
            final int rowIndex = rowAtomicInteger.incrementAndGet();
            final PtaReceiveInfo receiveInfo = sendInfo.getReceiveInfo();
            fillSendInfo(sheet, rowIndex, sendInfo, receiveInfo);
            Cell cell = JPoi.cell(sheet, rowIndex, 2);
            final String packType = sendInfo.getPackType();
            cell.setCellValue(Util.packTypeString(packType));
            cell = JPoi.cell(sheet, rowIndex, 3);
            final String transType = sendInfo.getTransType();
            cell.setCellValue(Util.transTypeString(transType));
            if ("2".equals(packType) || "3".equals(packType) || "5".equals(packType)) {
                cell = JPoi.cell(sheet, rowIndex, 20);
                final Integer packNo = sendInfo.getPackNo();
                if (packNo != null) {
                    cell.setCellValue(packNo);
                    cell = JPoi.cell(sheet, rowIndex, 21);
                    cell.setCellValue(sendInfo.getLfimg().doubleValue() / packNo);
                    cell = JPoi.cell(sheet, rowIndex, 22);
                    cell.setCellValue(receiveInfo.getLfimg().doubleValue() / packNo);
                }
            }
            cell = JPoi.cell(sheet, rowIndex, 23);
            cell.setCellValue(sendInfo.getBatchNo());
        });
    }

    private void fillMegData(XSSFSheet sheet, Stream<MegSendInfo> sendInfos) {
        fillHeadData(sheet);
        AtomicInteger rowAtomicInteger = new AtomicInteger();
        sendInfos.forEach(sendInfo -> {
            final int rowIndex = rowAtomicInteger.incrementAndGet();
            final MegReceiveInfo receiveInfo = sendInfo.getReceiveInfo();
            fillSendInfo(sheet, rowIndex, sendInfo, receiveInfo);
            Cell cell = JPoi.cell(sheet, rowIndex, 9);
            String value = Optional.ofNullable(sendInfo.getWharf())
                    .map(T001l::getLgobe)
                    .orElse(null);
            cell.setCellValue(value);
        });
    }

    private void fillHeadData(XSSFSheet sheet) {
        Cell cell = JPoi.cell(sheet, 0, 0);
        cell.setCellValue("日期");
        cell = JPoi.cell(sheet, 0, 1);
        cell.setCellValue("车牌号");
        cell = JPoi.cell(sheet, 0, 2);
        cell.setCellValue("包装方式");
        cell = JPoi.cell(sheet, 0, 3);
        cell.setCellValue("运输方式");
        cell = JPoi.cell(sheet, 0, 4);
        cell.setCellValue("驾驶员");
        cell = JPoi.cell(sheet, 0, 5);
        cell.setCellValue("物流公司");
        cell = JPoi.cell(sheet, 0, 6);
        cell.setCellValue("供应商");
        cell = JPoi.cell(sheet, 0, 7);
        cell.setCellValue("抬头");
        cell = JPoi.cell(sheet, 0, 8);
        cell.setCellValue("货源地");
        cell = JPoi.cell(sheet, 0, 9);
        cell.setCellValue("码头外库");
        cell = JPoi.cell(sheet, 0, 10);
        cell.setCellValue("卸货地");
        cell = JPoi.cell(sheet, 0, 11);
        cell.setCellValue("发货毛重");
        cell = JPoi.cell(sheet, 0, 12);
        cell.setCellValue("发货皮重");
        cell = JPoi.cell(sheet, 0, 13);
        cell.setCellValue("发货净重");
        cell = JPoi.cell(sheet, 0, 14);
        cell.setCellValue("收货毛重");
        cell = JPoi.cell(sheet, 0, 15);
        cell.setCellValue("收货皮重");
        cell = JPoi.cell(sheet, 0, 16);
        cell.setCellValue("收货净重");
        cell = JPoi.cell(sheet, 0, 17);
        cell.setCellValue("地磅调整数");
        cell = JPoi.cell(sheet, 0, 18);
        cell.setCellValue("特殊调整数");
        cell = JPoi.cell(sheet, 0, 19);
        cell.setCellValue("实际收货数量");
        cell = JPoi.cell(sheet, 0, 20);
        cell.setCellValue("包数");
        cell = JPoi.cell(sheet, 0, 21);
        cell.setCellValue("收货平均重量/包");
        cell = JPoi.cell(sheet, 0, 22);
        cell.setCellValue("发货平均重量/包");
        cell = JPoi.cell(sheet, 0, 23);
        cell.setCellValue("品种（生产线）");
        cell = JPoi.cell(sheet, 0, 24);
        cell.setCellValue("发货备注");
        cell = JPoi.cell(sheet, 0, 25);
        cell.setCellValue("收货备注");
        cell = JPoi.cell(sheet, 0, 26);
        cell.setCellValue("收货公司");
        cell = JPoi.cell(sheet, 0, 27);
        cell.setCellValue("录入人");
    }

    private void fillSendInfo(XSSFSheet sheet, int rowIndex, AbstractSendInfoEntity sendInfo, AbstractReceiveInfoEntity receiveInfo) {
        final Date receiveDate = receiveInfo.getReceiveDate();
        final LocalDate receiveLd = J.localDate(receiveDate);
        Cell cell = JPoi.cell(sheet, rowIndex, 0);
        cell.setCellValue(receiveLd.toString());
        cell = JPoi.cell(sheet, rowIndex, 1);
        cell.setCellValue(sendInfo.getCarNo());
        cell = JPoi.cell(sheet, rowIndex, 2);
        cell.setCellValue("槽车");
        cell = JPoi.cell(sheet, rowIndex, 3);
        cell.setCellValue("槽车");
        cell = JPoi.cell(sheet, rowIndex, 4);
        cell.setCellValue(sendInfo.getCarDriver());
        cell = JPoi.cell(sheet, rowIndex, 5);
        String value = Optional.ofNullable(sendInfo.getTransCorp())
                .map(TransCorp::getName)
                .orElse(null);
        cell.setCellValue(value);
        cell = JPoi.cell(sheet, rowIndex, 6);
        value = Optional.ofNullable(sendInfo.getLfa1())
                .map(Lfa1::getName1)
                .orElse(null);
        cell.setCellValue(value);
        cell = JPoi.cell(sheet, rowIndex, 7);
        value = Optional.ofNullable(sendInfo.getHeadInfo())
                .map(HeadInfo::getName)
                .orElse(null);
        cell.setCellValue(value);
        cell = JPoi.cell(sheet, rowIndex, 8);
        value = Optional.ofNullable(sendInfo.getSupplyInfo())
                .map(SupplyInfo::getName)
                .orElse(null);
        cell.setCellValue(value);
        cell = JPoi.cell(sheet, rowIndex, 10);
        value = Optional.ofNullable(receiveInfo.getT001l())
                .map(T001l::getLgobe)
                .orElse(null);
        cell.setCellValue(value);
        cell = JPoi.cell(sheet, rowIndex, 11);
        cell.setCellValue(sendInfo.getLfimg2().doubleValue());
        cell = JPoi.cell(sheet, rowIndex, 12);
        cell.setCellValue(sendInfo.getLfimg1().doubleValue());
        cell = JPoi.cell(sheet, rowIndex, 13);
        cell.setCellValue(sendInfo.getLfimg().doubleValue());
        cell = JPoi.cell(sheet, rowIndex, 14);
        cell.setCellValue(receiveInfo.getLfimg2().doubleValue());
        cell = JPoi.cell(sheet, rowIndex, 15);
        cell.setCellValue(receiveInfo.getLfimg1().doubleValue());
        cell = JPoi.cell(sheet, rowIndex, 16);
        cell.setCellValue(receiveInfo.getLfimg().doubleValue());
        cell = JPoi.cell(sheet, rowIndex, 17);
        cell.setCellValue(receiveInfo.getDiffLfimg1().doubleValue());
        cell = JPoi.cell(sheet, rowIndex, 18);
        cell.setCellValue(receiveInfo.getDiffLfimg2().doubleValue());
        cell = JPoi.cell(sheet, rowIndex, 19);
        cell.setCellValue(receiveInfo.getLfimg().doubleValue() - sendInfo.getLfimg().doubleValue() + receiveInfo.getDiffLfimg1().doubleValue() + receiveInfo.getDiffLfimg2().doubleValue());
        cell = JPoi.cell(sheet, rowIndex, 24);
        cell.setCellValue(sendInfo.getNote());
        cell = JPoi.cell(sheet, rowIndex, 25);
        cell.setCellValue(receiveInfo.getNote());
        cell = JPoi.cell(sheet, rowIndex, 26);
        cell.setCellValue(receiveInfo.getT001().getButxt());
        cell = JPoi.cell(sheet, rowIndex, 27);
        cell.setCellValue(sendInfo.getCreator().getName());
    }

}
