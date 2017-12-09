package com.hengyi.japp.cargo.interfaces.res.resources;

import com.hengyi.japp.cargo.application.GpsDataService;
import com.hengyi.japp.cargo.domain.gps.CarData;
import com.hengyi.japp.cargo.domain.gps.CarGpsData;
import org.hibernate.validator.constraints.NotBlank;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.Collection;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("gpsDatas")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class GpsDataResource {
    @Inject
    private GpsDataService gpsDataService;

    @Path("carNos/{carNo}")
    @GET
    public CarGpsData get(@Valid @NotBlank @PathParam("carNo") String carNo) throws Exception {
        return gpsDataService.getGpsData(carNo).orElse(null);
    }

    @Path("carNos")
    @GET
    public Collection<CarData> list() throws Exception {
        return gpsDataService.allCar();
    }

}
