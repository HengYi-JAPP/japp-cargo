package com.hengyi.japp.cargo.interfaces.res.resources;

import com.google.common.collect.Maps;
import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.repository.OperatorPermissionRepository;
import com.hengyi.japp.cargo.domain.repository.OperatorRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("auth")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class AuthResource {
    @Inject
    private OperatorRepository operatorRepository;
    @Inject
    private OperatorPermissionRepository operatorPermissionRepository;

    @GET
    public Map get(@Context SecurityContext sc) throws Exception {
        Map result = Maps.newHashMap();
        final Principal principal = sc.getUserPrincipal();
        final Operator operator = operatorRepository.find(principal);
        result.put("operator", operator);
        Optional.ofNullable(operatorPermissionRepository.find(principal))
                .ifPresent(operatorPermission -> {
                    result.put("permission", operatorPermission);
                });
        return result;
    }

}
