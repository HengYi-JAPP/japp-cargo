package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.repository.EkpoRepository;
import com.hengyi.japp.cargo.domain.sap.Ekpo;
import com.hengyi.japp.cargo.domain.sap.EkpoPK;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaEkpoRepository implements EkpoRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public Ekpo save(Ekpo t001) {
        return em.merge(t001);
    }

    @Override
    public Ekpo find(EkpoPK pk) {
        return em.find(Ekpo.class, pk);
    }

}
