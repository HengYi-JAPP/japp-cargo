package com.hengyi.japp.cargo.interfaces.res.resources;

import com.google.common.collect.ImmutableMap;
import com.hengyi.japp.cargo.application.PtaService;
import com.hengyi.japp.cargo.application.command.PtaSendInfoUpdateCommand;
import com.hengyi.japp.cargo.application.query.PtaSendInfoQuery;
import com.hengyi.japp.cargo.domain.pta.PtaSendInfo;
import com.hengyi.japp.cargo.domain.repository.PtaSendInfoRepository;
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
@Path("ptaSendInfos")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class PtaSendInfoResource {
    @Inject
    private PtaService ptaService;
    @Inject
    private PtaSendInfoRepository ptaSendInfoRepository;

    @POST
    public PtaSendInfo create(@Context SecurityContext sc,
                              @QueryParam("ylipsId") String ylipsId,
                              @Valid @NotNull PtaSendInfoUpdateCommand command) throws Exception {
        return ptaService.create(sc.getUserPrincipal(), ylipsId, command);
    }

    @Path("{id}")
    @PUT
    public PtaSendInfo update(@Context SecurityContext sc,
                              @QueryParam("ylipsId") String ylipsId,
                              @Valid @NotBlank @PathParam("id") String id,
                              @Valid @NotNull PtaSendInfoUpdateCommand command) throws Exception {
        return ptaService.update(sc.getUserPrincipal(), ylipsId, id, command);
    }

    @Path("{id}")
    @GET
    public PtaSendInfo get(@Context SecurityContext sc,
                           @Valid @NotBlank @PathParam("id") String id) throws Exception {
        return ptaSendInfoRepository.find(id);
    }

    @Path("{id}")
    @DELETE
    public void delete(@Context SecurityContext sc,
                       @Valid @NotBlank @PathParam("id") String id) throws Exception {
        ptaService.delete(sc.getUserPrincipal(), id);
    }

}
