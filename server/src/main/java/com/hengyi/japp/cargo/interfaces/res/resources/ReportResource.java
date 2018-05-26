package com.hengyi.japp.cargo.interfaces.res.resources;

import com.google.common.collect.ImmutableMap;
import com.hengyi.japp.cargo.application.command.DailyDetailExportTokenCommand;
import com.hengyi.japp.cargo.application.query.MegSendInfoQuery;
import com.hengyi.japp.cargo.application.query.PtaSendInfoQuery;
import com.hengyi.japp.cargo.domain.repository.MegSendInfoRepository;
import com.hengyi.japp.cargo.domain.repository.PtaSendInfoRepository;
import com.hengyi.japp.cargo.domain.sap.Ylips;
import com.hengyi.japp.cargo.interfaces.sap.SapService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hibernate.validator.constraints.NotBlank;
import org.jzb.J;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.hengyi.japp.cargo.Constant.JAPP_CARGO_PERMANENT_KEY;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("reports")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class ReportResource {
    @Inject
    private PtaSendInfoRepository ptaSendInfoRepository;
    @Inject
    private MegSendInfoRepository megSendInfoRepository;
    @Inject
    private SapService sapService;

    @Path("gpsYlipses")
    @GET
    public Collection<Ylips> gpsYlipses(@QueryParam("date") String date) throws Exception {
        LocalDate ld = J.isBlank(date) ? LocalDate.now() : LocalDate.parse(date);
        return sapService.queryYlips(ld)
                .filter(ylips ->
                        // 发往聚合物
                        Objects.equals(ylips.getLips().getLikp().getKunnr(), "0000100002")
                                // 槽车
                                && Objects.equals(ylips.getPackType(), "1")
                                && !Optional.ofNullable(ptaSendInfoRepository.queryBy(ylips))
                                .isPresent()
                )
                .collect(Collectors.toSet());
    }

    @Path("sendInfos")
    @GET
    public Map sendInfos(@Context SecurityContext sc,
                         @Valid @NotBlank @QueryParam("date") String date) throws Exception {
        final Principal principal = sc.getUserPrincipal();
        LocalDate ld = J.isBlank(date) ? LocalDate.now() : LocalDate.parse(date);
        return ImmutableMap.of(
                "ptas", ptaSendInfoRepository.querySendInfo(principal, ld).collect(Collectors.toSet()),
                "megs", megSendInfoRepository.querySendInfo(principal, ld).collect(Collectors.toSet())
        );
    }

    @Path("dailyDetail")
    @GET
    public Map dailyDetail(@Context SecurityContext sc,
                           @Valid @NotBlank @QueryParam("startDate") String startDate,
                           @Valid @NotBlank @QueryParam("endDate") String endDate,
                           @Valid @Min(0) @QueryParam("first") @DefaultValue("0") int first,
                           @Valid @Min(10) @QueryParam("pageSize") @DefaultValue("1000") int pageSize) throws Exception {
        final Principal principal = sc.getUserPrincipal();
        // final LocalDate ld = J.isBlank(date) ? LocalDate.now() : LocalDate.parse(date);
        final LocalDate ldStart = J.isBlank(startDate) ? LocalDate.now() : LocalDate.parse(startDate);
        final LocalDate ldEnd = J.isBlank(endDate) ? LocalDate.now() : LocalDate.parse(endDate);
        PtaSendInfoQuery ptaQuery = new PtaSendInfoQuery(principal);
        // ptaQuery.ld = ld;
        ptaQuery.ldStart = ldStart;
        ptaQuery.ldEnd = ldEnd;
        ptaSendInfoRepository.query(ptaQuery);
        MegSendInfoQuery megQuery = new MegSendInfoQuery(principal);
        // megQuery.ld = ld;
        megQuery.ldStart = ldStart;
        megQuery.ldEnd = ldEnd;
        megSendInfoRepository.query(megQuery);
        return ImmutableMap.of(
                "ptas", ptaQuery.result.collect(Collectors.toSet()),
                "megs", megQuery.result.collect(Collectors.toSet())
        );
    }

    @Path("dailyDetailExportToken")
    @POST
    @Produces(TEXT_PLAIN)
    public String dailyDetail(@Context SecurityContext sc,
                              @Valid @NotNull DailyDetailExportTokenCommand command) throws Exception {
        final Principal principal = sc.getUserPrincipal();
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, JAPP_CARGO_PERMANENT_KEY)
                .setIssuedAt(new Date())
                .setIssuer(principal.getName())
                .setAudience(principal.getName())
                .claim("command", command)
                .compact();
    }

}
