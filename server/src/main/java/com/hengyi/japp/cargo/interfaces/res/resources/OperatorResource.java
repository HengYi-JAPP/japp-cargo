package com.hengyi.japp.cargo.interfaces.res.resources;

import com.google.common.collect.ImmutableMap;
import com.hengyi.japp.cargo.application.OperatorService;
import com.hengyi.japp.cargo.application.command.OperatorPermissionUpdateCommand;
import com.hengyi.japp.cargo.application.query.OperatorQuery;
import com.hengyi.japp.cargo.domain.OperatorPermission;
import com.hengyi.japp.cargo.domain.repository.OperatorPermissionRepository;
import com.hengyi.japp.cargo.domain.repository.OperatorRepository;
import org.hibernate.validator.constraints.NotBlank;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("operators")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class OperatorResource {
    @Inject
    private OperatorRepository operatorRepository;
    @Inject
    private OperatorPermissionRepository operatorPermissionRepository;
    @Inject
    private OperatorService operatorService;

    @GET
    public Map list(@Context SecurityContext sc,
                    @QueryParam("q") String q,
                    @QueryParam("defaultReceiveBukrs") Set<String> defaultReceiveBukrsSet,
                    @Valid @Min(0) @QueryParam("first") int first,
                    @Min(10) @QueryParam("pageSize") int pageSize) throws Exception {
        final Principal principal = sc.getUserPrincipal();
        final OperatorQuery query = new OperatorQuery(principal, first, pageSize);
        query.q = q;
        query.defaultReceiveBukrsSet = defaultReceiveBukrsSet;
        query.exe(operatorRepository);
        return ImmutableMap.of("count", query.count, "operators", query.result.collect(Collectors.toSet()));
    }

    @Path("{id}/permission")
    @GET
    public OperatorPermission get(@Context SecurityContext sc,
                                  @Valid @NotBlank @PathParam("id") String id) throws Exception {
        return operatorPermissionRepository.find(id);
    }

    @Path("{id}/permission")
    @PUT
    public OperatorPermission get(@Context SecurityContext sc,
                                  @Valid @NotBlank @PathParam("id") String id,
                                  @Valid @NotNull OperatorPermissionUpdateCommand command) throws Exception {
        return operatorService.update(sc.getUserPrincipal(), id, command);
    }

}
