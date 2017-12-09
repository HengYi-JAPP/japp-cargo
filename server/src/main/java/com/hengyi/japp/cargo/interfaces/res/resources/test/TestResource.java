package com.hengyi.japp.cargo.interfaces.res.resources.test;

import com.hengyi.japp.cargo.application.internal.SapSyncSchedule;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("test")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class TestResource {
    @Inject
    SapSyncSchedule sapSyncSchedule;

    @Path("sapSync")
    @GET
    public void get() throws Exception {
        sapSyncSchedule.sync();
    }

}
