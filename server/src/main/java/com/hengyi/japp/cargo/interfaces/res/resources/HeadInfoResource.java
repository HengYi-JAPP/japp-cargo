package com.hengyi.japp.cargo.interfaces.res.resources;

import com.hengyi.japp.cargo.application.HeadInfoService;
import com.hengyi.japp.cargo.application.command.HeadInfoUpdateCommand;
import com.hengyi.japp.cargo.domain.config.HeadInfo;
import com.hengyi.japp.cargo.domain.repository.HeadInfoRepository;
import com.hengyi.japp.cargo.domain.repository.T001Repository;
import com.hengyi.japp.cargo.domain.sap.T001;
import org.hibernate.validator.constraints.NotBlank;
import org.jzb.J;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.Collection;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("headInfos")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class HeadInfoResource {
    @Inject
    private HeadInfoRepository headInfoRepository;
    @Inject
    private HeadInfoService headInfoService;
    @Inject
    private T001Repository t001Repository;

    @POST
    public HeadInfo create(@Context SecurityContext sc, @Valid @NotNull HeadInfoUpdateCommand command) throws Exception {
        return headInfoService.create(sc.getUserPrincipal(), command);
    }

    @Path("{id}")
    @PUT
    public HeadInfo update(@Context SecurityContext sc,
                           @Valid @NotBlank @PathParam("id") String id,
                           @Valid @NotNull HeadInfoUpdateCommand command) throws Exception {
        return headInfoService.update(sc.getUserPrincipal(), id, command);
    }

    @Path("{id}")
    @GET
    public HeadInfo get(@Context SecurityContext sc, @Valid @NotBlank @PathParam("id") String id) throws Exception {
        return headInfoRepository.find(id);
    }

    @GET
    public Collection<HeadInfo> list(@Context SecurityContext sc, @QueryParam("bukrs") String bukrs) throws Exception {
        if (J.nonBlank(bukrs)) {
            final T001 t001 = t001Repository.find(bukrs);
            return headInfoRepository.queryBy(t001).collect(Collectors.toSet());
        }
        return headInfoRepository.queryAll().collect(Collectors.toSet());
    }

    @Path("{id}")
    @DELETE
    public void delete(@Context SecurityContext sc, @Valid @NotBlank @PathParam("id") String id) throws Exception {
        headInfoService.delete(sc.getUserPrincipal(), id);
    }

    @Path("{id}/t001s")
    @GET
    public Collection<T001> listT001(@Context SecurityContext sc, @Valid @NotBlank @PathParam("id") String id) throws Exception {
        return headInfoRepository.find(id).getT001s();
    }

}
