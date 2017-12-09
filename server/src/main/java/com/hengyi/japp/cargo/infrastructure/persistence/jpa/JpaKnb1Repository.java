package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.repository.Knb1Repository;
import com.hengyi.japp.cargo.domain.sap.Knb1;
import com.hengyi.japp.cargo.domain.sap.Knb1PK;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaKnb1Repository implements Knb1Repository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public Knb1 save(Knb1 t001) {
        return em.merge(t001);
    }

    @Override
    public Knb1 find(Knb1PK pk) {
        return em.find(Knb1.class, pk);
    }

}
