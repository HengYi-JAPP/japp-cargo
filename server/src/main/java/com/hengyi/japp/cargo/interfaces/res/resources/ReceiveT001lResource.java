package com.hengyi.japp.cargo.interfaces.res.resources;

import com.hengyi.japp.cargo.application.ReceiveT001lService;
import com.hengyi.japp.cargo.domain.config.ReceiveT001l;
import com.hengyi.japp.cargo.domain.repository.ReceiveT001lRepository;
import com.hengyi.japp.cargo.domain.repository.T001Repository;
import com.hengyi.japp.cargo.domain.sap.T001lPK;
import org.hibernate.validator.constraints.NotBlank;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("receiveT001ls")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class ReceiveT001lResource {
    @Inject
    private ReceiveT001lService receiveT001lService;
    @Inject
    private ReceiveT001lRepository receiveT001lRepository;
    @Inject
    private T001Repository t001Repository;

    @POST
    public ReceiveT001l add(@Context SecurityContext sc, @Valid @NotNull T001lPK pk) throws Exception {
        return receiveT001lService.add(sc.getUserPrincipal(), pk);
    }

    @DELETE
    public void delete(@Context SecurityContext sc,
                       @Valid @NotBlank @QueryParam("werks") String werks,
                       @Valid @NotBlank @QueryParam("lgort") String lgort) throws Exception {
        receiveT001lService.remove(sc.getUserPrincipal(), new T001lPK(werks, lgort));
    }

    @GET
    public Collection<ReceiveT001l> list(@Context SecurityContext sc,
                                         @QueryParam("bukrs") String bukrs) throws Exception {
        return Optional.ofNullable(bukrs)
                .map(t001Repository::find)
                .map(receiveT001lRepository::queryBy)
                .orElseGet(() -> receiveT001lRepository.queryAll())
                .collect(Collectors.toSet());
    }

}
