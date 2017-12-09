package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.config.ReceiveT001;
import com.hengyi.japp.cargo.domain.repository.ReceiveT001Repository;
import org.jzb.J;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaReceiveT001Repository implements ReceiveT001Repository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public ReceiveT001 save(ReceiveT001 t001) {
        if (J.isBlank(t001.getBukrs())) {
            t001.setBukrs(t001.getT001().getBukrs());
        }
        return em.merge(t001);
    }

    @Override
    public ReceiveT001 find(String id) {
        return em.find(ReceiveT001.class, id);
    }

    @Override
    public Stream<ReceiveT001> queryAll() {
        return em.createNamedQuery("ReceiveT001.queryAll", ReceiveT001.class)
                .getResultList()
                .stream();
    }

}
