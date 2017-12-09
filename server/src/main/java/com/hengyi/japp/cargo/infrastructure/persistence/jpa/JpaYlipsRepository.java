package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.repository.YlipsRepository;
import com.hengyi.japp.cargo.domain.sap.Likp;
import com.hengyi.japp.cargo.domain.sap.Lips;
import com.hengyi.japp.cargo.domain.sap.Ylips;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaYlipsRepository implements YlipsRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public Ylips save(Ylips ylips) {
        final Lips lips = ylips.getLips();
        final Likp likp = lips.getLikp();
        em.merge(likp);
        em.merge(lips);
        return em.merge(ylips);
    }

    @Override
    public Ylips find(String id) {
        return em.find(Ylips.class, id);
    }

}
