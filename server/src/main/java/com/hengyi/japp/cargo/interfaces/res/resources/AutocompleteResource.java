package com.hengyi.japp.cargo.interfaces.res.resources;

import com.hengyi.japp.cargo.domain.CarNoInfoLog;
import com.hengyi.japp.cargo.domain.repository.CarNoInfoLogRepository;
import com.hengyi.japp.cargo.domain.repository.Kna1Repository;
import com.hengyi.japp.cargo.domain.repository.Lfa1Repository;
import com.hengyi.japp.cargo.domain.repository.T001Repository;
import com.hengyi.japp.cargo.domain.sap.Kna1;
import com.hengyi.japp.cargo.domain.sap.Lfa1;
import com.hengyi.japp.cargo.domain.sap.T001;
import org.jzb.J;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("autocompletes")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class AutocompleteResource {
    @Inject
    private Lfa1Repository lfa1Repository;
    @Inject
    private Kna1Repository kna1Repository;
    @Inject
    private T001Repository t001Repository;
    @Inject
    private CarNoInfoLogRepository carNoInfoLogRepository;

    @Path("kna1")
    @GET
    public Collection<Kna1> kna1(@Context SecurityContext sc,
                                 @QueryParam("bukrs") String bukrs,
                                 @QueryParam("q") String q) throws Exception {
        return Optional.ofNullable(q)
                .filter(J::nonBlank)
                .map(it -> {
                    T001 t001 = Optional.ofNullable(bukrs)
                            .filter(J::nonBlank)
                            .map(t001Repository::find)
                            .orElse(null);
                    return kna1Repository.autocomplete(t001, q).collect(Collectors.toSet());
                })
                .orElse(Collections.emptySet());
    }

    @Path("lfa1")
    @GET
    public Collection<Lfa1> lfa1(@Context SecurityContext sc,
                                 @QueryParam("bukrs") String bukrs,
                                 @QueryParam("q") String q) throws Exception {
        return Optional.ofNullable(q)
                .filter(J::nonBlank)
                .map(it -> {
                    T001 t001 = Optional.ofNullable(bukrs)
                            .filter(J::nonBlank)
                            .map(t001Repository::find)
                            .orElse(null);
                    return lfa1Repository.autocomplete(t001, q).collect(Collectors.toSet());
                })
                .orElse(Collections.emptySet());
    }

    @Path("carNoInfoLog")
    @GET
    public Collection<CarNoInfoLog> lfa1(@Context SecurityContext sc,
                                         @QueryParam("q") String q) throws Exception {
        return Optional.ofNullable(q)
                .filter(J::nonBlank)
                .map(it ->
                        carNoInfoLogRepository.autocomplete(it).collect(Collectors.toSet())
                )
                .orElse(Collections.emptySet());
    }

}
