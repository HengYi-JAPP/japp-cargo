package com.hengyi.japp.cargo.interfaces.res.resources;

import com.hengyi.japp.cargo.domain.OperatorPermission;
import com.hengyi.japp.cargo.domain.config.ReceiveT001;
import com.hengyi.japp.cargo.domain.repository.OperatorPermissionRepository;
import com.hengyi.japp.cargo.domain.repository.ReceiveT001Repository;
import com.hengyi.japp.cargo.domain.repository.YlipsRepository;
import com.hengyi.japp.cargo.domain.sap.Likp;
import com.hengyi.japp.cargo.domain.sap.Lips;
import com.hengyi.japp.cargo.domain.sap.T001;
import com.hengyi.japp.cargo.domain.sap.Ylips;
import com.hengyi.japp.cargo.interfaces.sap.SapService;
import org.jzb.J;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * Created by jzb on 16-10-26.
 */
@Stateless
@Path("sapYlipses")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
public class SapYlipsResource {
    @Inject
    private SapService sapService;
    @Inject
    private YlipsRepository ylipsRepository;
    @Inject
    private ReceiveT001Repository receiveT001Repository;
    @Inject
    private OperatorPermissionRepository operatorPermissionRepository;

    @GET
    public Collection<Ylips> get(@Context SecurityContext sc,
                                 @QueryParam("date") String date) throws Exception {
        final Principal principal = sc.getUserPrincipal();
        final String defaultBukrs = Optional.ofNullable(operatorPermissionRepository.find(principal))
                .map(OperatorPermission::getDefaultReceiveT001)
                .map(T001::getBukrs)
                .orElse(null);
        final Map<String, ReceiveT001> kunnrMap = receiveT001Repository.queryAll()
                .filter(it -> Objects.nonNull(it.getKna1()))
                .collect(Collectors.toMap(it -> it.getKna1().getId(), Function.identity()));
        LocalDate ld = J.isBlank(date) ? LocalDate.now() : LocalDate.parse(date);
        return sapService.queryYlips(ld)
                .filter(ylips -> ylipsRepository.find(ylips.getId()) == null)
                .filter(ylips -> {
                    final String kunnr = Optional.ofNullable(ylips.getLips())
                            .map(Lips::getLikp)
                            .map(Likp::getKunnr)
                            .orElse(null);
                    if (kunnr == null) {
                        return false;
                    }
                    final ReceiveT001 receiveT001 = kunnrMap.get(kunnr);
                    if (receiveT001 == null) {
                        // 没有明确对应的收货公司，属于公共收货，任何公司都可以收这笔货
                        return true;
                    }
                    return Objects.equals(receiveT001.getBukrs(), defaultBukrs);
                })
                .collect(Collectors.toSet());
    }

}
