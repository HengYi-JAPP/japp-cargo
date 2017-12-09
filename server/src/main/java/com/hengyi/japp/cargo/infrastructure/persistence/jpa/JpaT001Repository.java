package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.repository.T001Repository;
import com.hengyi.japp.cargo.domain.repository.T001kRepository;
import com.hengyi.japp.cargo.domain.sap.T001;
import com.hengyi.japp.cargo.domain.sap.T001k;
import com.hengyi.japp.cargo.domain.sap.T001w;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaT001Repository implements T001Repository, Serializable {
    @Inject
    private EntityManager em;
    @Inject
    private T001kRepository t001kRepository;

    @Override
    public T001 save(T001 t001) {
        return em.merge(t001);
    }

    @Override
    public T001 find(T001w t001w) {
        return Optional.ofNullable(t001kRepository.find(t001w.getWerks()))
                .map(T001k::getBukrs)
                .map(this::find)
                .orElse(null);
    }

    @Override
    public T001 find(String id) {
        return em.find(T001.class, id);
    }

    @Override
    public Stream<T001> queryAll() {
        return em.createNamedQuery("T001.queryAll", T001.class)
                .getResultList()
                .stream();
    }

}
