package com.hengyi.japp.cargo.interfaces.res.resources;

import com.hengyi.japp.cargo.domain.CarNoInfoLog;
import com.hengyi.japp.cargo.domain.repository.CarNoInfoLogRepository;
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
@Path("carNoInfoLogs")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class CarNoInfoLogResource {
    @Inject
    private CarNoInfoLogRepository carNoInfoLogRepository;

    @Path("{carNo}")
    @GET
    public CarNoInfoLog get(@Context SecurityContext sc,
                            @Valid @NotBlank @PathParam("carNo") String carNo) throws Exception {
        return carNoInfoLogRepository.find(carNo);
    }


}
