package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.repository.T001wRepository;
import com.hengyi.japp.cargo.domain.sap.T001l;
import com.hengyi.japp.cargo.domain.sap.T001w;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaT001wRepository implements T001wRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public T001w save(T001w t001) {
        return em.merge(t001);
    }

    @Override
    public T001w find(String id) {
        return em.find(T001w.class, id);
    }

    @Override
    public T001w find(T001l t001l) {
        return find(t001l.getWerks());
    }

    @Override
    public Stream<T001w> queryAll() {
        return em.createNamedQuery("T001w.queryAll", T001w.class)
                .getResultList()
                .stream();
    }

}
