package com.hengyi.japp.cargo.interfaces.res.resources;

import com.google.common.collect.ImmutableMap;
import com.hengyi.japp.cargo.application.OperatorService;
import com.hengyi.japp.cargo.application.command.OperatorPermissionUpdateCommand;
import com.hengyi.japp.cargo.domain.Operator;
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
import java.util.Collection;
import java.util.Map;
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
                    @Valid @Min(0) @QueryParam("first") @DefaultValue("0") int first,
                    @Valid @Min(10) @QueryParam("pageSize") @DefaultValue("10") int pageSize) throws Exception {
        final long count = operatorRepository.count();
        final Collection<Operator> operators = operatorRepository.queryAll(first, pageSize).collect(Collectors.toSet());
        return ImmutableMap.of("count", count, "operators", operators);
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
