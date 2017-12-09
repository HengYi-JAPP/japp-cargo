package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.repository.EkkoRepository;
import com.hengyi.japp.cargo.domain.sap.Ekko;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaEkkoRepository implements EkkoRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public Ekko save(Ekko t001) {
        return em.merge(t001);
    }

    @Override
    public Ekko find(String id) {
        return em.find(Ekko.class, id);
    }

}
