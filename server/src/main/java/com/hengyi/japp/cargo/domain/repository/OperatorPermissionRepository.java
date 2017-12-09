package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.OperatorPermission;

import java.security.Principal;

public interface OperatorPermissionRepository {

    OperatorPermission find(Principal principal);

    OperatorPermission find(String id);

    OperatorPermission save(OperatorPermission operatorPermission);
}
