package com.hengyi.japp.cargo.interfaces.res.resources;

import com.hengyi.japp.cargo.application.SupplyInfoService;
import com.hengyi.japp.cargo.application.command.SupplyInfoUpdateCommand;
import com.hengyi.japp.cargo.domain.config.SupplyInfo;
import com.hengyi.japp.cargo.domain.repository.SupplyInfoRepository;
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
@Path("supplyInfos")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class SupplyInfoResource {
    @Inject
    private SupplyInfoRepository supplyInfoRepository;
    @Inject
    private SupplyInfoService supplyInfoService;
    @Inject
    private T001Repository t001Repository;

    @POST
    public SupplyInfo create(@Context SecurityContext sc, @Valid @NotNull SupplyInfoUpdateCommand command) throws Exception {
        return supplyInfoService.create(sc.getUserPrincipal(), command);
    }

    @Path("{id}")
    @PUT
    public SupplyInfo update(@Context SecurityContext sc,
                           @Valid @NotBlank @PathParam("id") String id,
                           @Valid @NotNull SupplyInfoUpdateCommand command) throws Exception {
        return supplyInfoService.update(sc.getUserPrincipal(), id, command);
    }

    @Path("{id}")
    @GET
    public SupplyInfo get(@Context SecurityContext sc, @Valid @NotBlank @PathParam("id") String id) throws Exception {
        return supplyInfoRepository.find(id);
    }

    @GET
    public Collection<SupplyInfo> list(@Context SecurityContext sc, @QueryParam("bukrs") String bukrs) throws Exception {
        if (J.nonBlank(bukrs)) {
            final T001 t001 = t001Repository.find(bukrs);
            return supplyInfoRepository.queryBy(t001).collect(Collectors.toSet());
        }
        return supplyInfoRepository.queryAll().collect(Collectors.toSet());
    }

    @Path("{id}")
    @DELETE
    public void delete(@Context SecurityContext sc, @Valid @NotBlank @PathParam("id") String id) throws Exception {
        supplyInfoService.delete(sc.getUserPrincipal(), id);
    }

    @Path("{id}/t001s")
    @GET
    public Collection<T001> listT001(@Context SecurityContext sc, @Valid @NotBlank @PathParam("id") String id) throws Exception {
        return supplyInfoRepository.find(id).getT001s();
    }

}
