package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.repository.T001kRepository;
import com.hengyi.japp.cargo.domain.sap.T001k;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.stream.Stream;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaT001kRepository implements T001kRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public T001k save(T001k t001) {
        return em.merge(t001);
    }

    @Override
    public T001k find(String id) {
        return em.find(T001k.class, id);
    }

    @Override
    public Stream<T001k> queryAll() {
        return em.createNamedQuery("T001k.queryAll", T001k.class)
                .getResultList()
                .stream();
    }

}
