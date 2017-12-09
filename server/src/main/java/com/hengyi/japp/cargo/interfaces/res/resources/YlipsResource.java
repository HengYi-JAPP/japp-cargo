package com.hengyi.japp.cargo.interfaces.res.resources;

import com.hengyi.japp.cargo.domain.sap.Ylips;
import com.hengyi.japp.cargo.interfaces.sap.SapService;
import org.hibernate.validator.constraints.NotBlank;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("ylipses")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class YlipsResource {
    @Inject
    private SapService sapService;

    @Path("{id}")
    @GET
    public Ylips get(@Context SecurityContext sc,
                     @Valid @NotBlank @PathParam("id") String id) throws Exception {
        return sapService.findYlips(id);
    }

}
