package com.hengyi.japp.cargo.interfaces.res.resources;

import com.google.common.collect.ImmutableMap;
import com.hengyi.japp.cargo.application.MegService;
import com.hengyi.japp.cargo.application.command.MegSendInfoUpdateCommand;
import com.hengyi.japp.cargo.application.query.MegSendInfoQuery;
import com.hengyi.japp.cargo.domain.meg.MegSendInfo;
import com.hengyi.japp.cargo.domain.repository.MegSendInfoRepository;
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
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("megSendInfos")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class MegSendInfoResource {
    @Inject
    private MegService megService;
    @Inject
    private MegSendInfoRepository megSendInfoRepository;

    @POST
    public MegSendInfo create(@Context SecurityContext sc,
                              @Valid @NotNull MegSendInfoUpdateCommand command) throws Exception {
        return megService.create(sc.getUserPrincipal(), command);
    }

    @Path("{id}")
    @PUT
    public MegSendInfo update(@Context SecurityContext sc,
                              @Valid @NotBlank @PathParam("id") String id,
                              @Valid @NotNull MegSendInfoUpdateCommand command) throws Exception {
        return megService.update(sc.getUserPrincipal(), id, command);
    }

    @Path("{id}")
    @GET
    public MegSendInfo get(@Context SecurityContext sc,
                           @Valid @NotBlank @PathParam("id") String id) throws Exception {
        return megSendInfoRepository.find(id);
    }

    @Path("{id}")
    @DELETE
    public void delete(@Context SecurityContext sc,
                       @Valid @NotBlank @PathParam("id") String id) throws Exception {
        megService.delete(sc.getUserPrincipal(), id);
    }

    @GET
    public Map<String, Object> list(@Context SecurityContext sc,
                                    @QueryParam("date") String date,
                                    @QueryParam("startDate") String dateStart,
                                    @QueryParam("endDate") String dateEnd,
                                    @Valid @Min(0) @QueryParam("first") int first,
                                    @Valid @Min(10) @QueryParam("pageSize") int pageSize) throws Exception {
        MegSendInfoQuery query = new MegSendInfoQuery(sc.getUserPrincipal(), first, pageSize);
        Optional.ofNullable(date).filter(J::nonBlank).map(LocalDate::parse).ifPresent(it -> query.ld = it);
        Optional.ofNullable(dateStart).filter(J::nonBlank).map(LocalDate::parse).ifPresent(it -> query.ldStart = it);
        Optional.ofNullable(dateEnd).filter(J::nonBlank).map(LocalDate::parse).ifPresent(it -> query.ldEnd = it);
        query.exe(megSendInfoRepository);
        return ImmutableMap.of(
                "count", query.count,
                "sendInfos", query.result.collect(Collectors.toSet())
        );
    }

}
