package com.hengyi.japp.cargo.application.internal;

import com.hengyi.japp.cargo.application.AuthService;
import com.hengyi.japp.cargo.domain.OperatorPermission;
import com.hengyi.japp.cargo.domain.repository.OperatorPermissionRepository;
import com.hengyi.japp.cargo.domain.repository.T001Repository;
import com.hengyi.japp.cargo.domain.sap.T001;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Principal;
import java.util.Optional;

/**
 * 描述：
 *
 * @author jzb 2017-12-02
 */
@Stateless
public class AuthServiceImpl implements AuthService {
    @Inject
    private T001Repository t001Repository;
    @Inject
    private OperatorPermissionRepository operatorPermissionRepository;

    @Override
    public T001 defaultT001(Principal principal) {
        return Optional.ofNullable(operatorPermissionRepository.find(principal))
                .map(OperatorPermission::getDefaultReceiveT001)
                .orElseGet(() -> t001Repository.find("9300"));
    }
}
