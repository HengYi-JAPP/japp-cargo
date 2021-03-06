package com.hengyi.japp.cargo.interfaces.res.resources;

import com.hengyi.japp.cargo.domain.repository.T001kRepository;
import com.hengyi.japp.cargo.domain.repository.T001lRepository;
import com.hengyi.japp.cargo.domain.sap.T001l;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.Collection;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("t001ls")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class T001lResource {
    @Inject
    private T001lRepository t001lRepository;
    @Inject
    private T001kRepository t001kRepository;

    @GET
    public Collection<T001l> list(@Context SecurityContext sc) throws Exception {
        return t001lRepository.queryAll().collect(Collectors.toSet());
    }

}
