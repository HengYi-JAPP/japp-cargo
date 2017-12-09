package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.OperatorPermission;
import com.hengyi.japp.cargo.domain.repository.OperatorPermissionRepository;
import org.jzb.J;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.security.Principal;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaOperatorPermissionRepository implements OperatorPermissionRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public OperatorPermission find(String id) {
        return em.find(OperatorPermission.class, id);
    }

    @Override
    public OperatorPermission find(Principal principal) {
        return find(principal.getName());
    }

    @Override
    public OperatorPermission save(OperatorPermission operatorPermission) {
        if (J.isBlank(operatorPermission.getId())) {
            operatorPermission.setId(operatorPermission.getOperator().getId());
        }
        return em.merge(operatorPermission);
    }

}
