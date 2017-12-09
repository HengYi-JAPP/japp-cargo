package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.repository.Lfb1Repository;
import com.hengyi.japp.cargo.domain.sap.Lfb1;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaLfb1Repository implements Lfb1Repository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public Lfb1 save(Lfb1 t001) {
        return em.merge(t001);
    }

}
