package com.hengyi.japp.cargo.interfaces.res.resources;

import com.hengyi.japp.cargo.application.TransCorpService;
import com.hengyi.japp.cargo.application.command.TransCorpUpdateCommand;
import com.hengyi.japp.cargo.domain.config.TransCorp;
import com.hengyi.japp.cargo.domain.repository.T001Repository;
import com.hengyi.japp.cargo.domain.repository.TransCorpRepository;
import com.hengyi.japp.cargo.domain.sap.T001;
import org.hibernate.validator.constraints.NotBlank;
import org.jzb.J;

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
@Path("transCorps")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class TransCorpResource {
    @Inject
    private TransCorpRepository transCorpRepository;
    @Inject
    private TransCorpService transCorpService;
    @Inject
    private T001Repository t001Repository;

    @POST
    public TransCorp create(@Context SecurityContext sc, @Valid @NotNull TransCorpUpdateCommand command) throws Exception {
        return transCorpService.create(sc.getUserPrincipal(), command);
    }

    @Path("{id}")
    @PUT
    public TransCorp update(@Context SecurityContext sc,
                            @Valid @NotBlank @PathParam("id") String id,
                            @Valid @NotNull TransCorpUpdateCommand command) throws Exception {
        return transCorpService.update(sc.getUserPrincipal(), id, command);
    }

    @Path("{id}")
    @GET
    public TransCorp get(@Context SecurityContext sc, @Valid @NotBlank @PathParam("id") String id) throws Exception {
        return transCorpRepository.find(id);
    }

    @GET
    public Collection<TransCorp> list(@Context SecurityContext sc, @QueryParam("bukrs") String bukrs) throws Exception {
        if (J.nonBlank(bukrs)) {
            final T001 t001 = t001Repository.find(bukrs);
            return transCorpRepository.queryBy(t001).collect(Collectors.toSet());
        }
        return transCorpRepository.queryAll().collect(Collectors.toSet());
    }

    @Path("{id}")
    @DELETE
    public void delete(@Context SecurityContext sc, @Valid @NotBlank @PathParam("id") String id) throws Exception {
        transCorpService.delete(sc.getUserPrincipal(), id);
    }

    @Path("{id}/t001s")
    @GET
    public Collection<T001> listT001(@Context SecurityContext sc, @Valid @NotBlank @PathParam("id") String id) throws Exception {
        return transCorpRepository.find(id).getT001s();
    }

}
