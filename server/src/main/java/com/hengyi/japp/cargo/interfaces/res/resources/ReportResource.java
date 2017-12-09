package com.hengyi.japp.cargo.interfaces.res.resources;

import com.google.common.collect.ImmutableMap;
import com.hengyi.japp.cargo.application.query.MegSendInfoQuery;
import com.hengyi.japp.cargo.application.query.PtaSendInfoQuery;
import com.hengyi.japp.cargo.domain.repository.MegSendInfoRepository;
import com.hengyi.japp.cargo.domain.repository.PtaSendInfoRepository;
import com.hengyi.japp.cargo.domain.sap.Ylips;
import com.hengyi.japp.cargo.interfaces.sap.SapService;
import org.jzb.J;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

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
                )
                .collect(Collectors.toSet());
    }

    @Path("sendInfos")
    @GET
    public Map get(@Context SecurityContext sc,
                   @QueryParam("date") String date) throws Exception {
        final Principal principal = sc.getUserPrincipal();
        LocalDate ld = J.isBlank(date) ? LocalDate.now() : LocalDate.parse(date);
        PtaSendInfoQuery ptaQuery = new PtaSendInfoQuery(principal);
        ptaQuery.ld = ld;
        ptaQuery.exe(ptaSendInfoRepository);
        MegSendInfoQuery megQuery = new MegSendInfoQuery(principal);
        megQuery.ld = ld;
        megQuery.exe(megSendInfoRepository);
        return ImmutableMap.of(
                "ptas", ptaQuery.result.collect(Collectors.toSet()),
                "megs", megQuery.result.collect(Collectors.toSet())
        );
    }

}
