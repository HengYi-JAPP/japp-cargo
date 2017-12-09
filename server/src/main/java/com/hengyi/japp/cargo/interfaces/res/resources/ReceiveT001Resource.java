package com.hengyi.japp.cargo.interfaces.res.resources;

import com.hengyi.japp.cargo.application.ReceiveT001Service;
import com.hengyi.japp.cargo.application.command.ReceiveT001AddCommand;
import com.hengyi.japp.cargo.domain.config.ReceiveT001;
import com.hengyi.japp.cargo.domain.repository.ReceiveT001Repository;
import org.hibernate.validator.constraints.NotBlank;

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
@Path("receiveT001s")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class ReceiveT001Resource {
    @Inject
    private ReceiveT001Service receiveT001Service;
    @Inject
    private ReceiveT001Repository receiveT001Repository;

    @POST
    public ReceiveT001 update(@Context SecurityContext sc,
                              @Valid @NotNull ReceiveT001AddCommand command) throws Exception {
        return receiveT001Service.add(sc.getUserPrincipal(), command);
    }

    @Path("{bukrs}")
    @DELETE
    public void delete(@Context SecurityContext sc,
                       @Valid @NotBlank @PathParam("bukrs") String bukrs) throws Exception {
        receiveT001Service.remove(sc.getUserPrincipal(), bukrs);
    }

    @GET
    public Collection<ReceiveT001> list(@Context SecurityContext sc) throws Exception {
        return receiveT001Repository.queryAll().collect(Collectors.toSet());
    }

}
