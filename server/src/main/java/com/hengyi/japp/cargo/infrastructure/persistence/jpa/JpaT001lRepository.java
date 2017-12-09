package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.config.ReceiveT001;
import com.hengyi.japp.cargo.domain.repository.ReceiveT001Repository;
import com.hengyi.japp.cargo.domain.repository.T001kRepository;
import com.hengyi.japp.cargo.domain.repository.T001lRepository;
import com.hengyi.japp.cargo.domain.sap.T001k;
import com.hengyi.japp.cargo.domain.sap.T001l;
import com.hengyi.japp.cargo.domain.sap.T001lPK;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaT001lRepository implements T001lRepository, Serializable {
    @Inject
    private EntityManager em;
    @Inject
    private ReceiveT001Repository receiveT001Repository;
    @Inject
    private T001kRepository t001kRepository;

    @Override
    public T001l save(T001l t001) {
        return em.merge(t001);
    }

    @Override
    public T001l find(T001lPK pk) {
        return em.find(T001l.class, pk);
    }

    @Override
    public Stream<T001l> queryAll() {
        final Set<String> bukrses = receiveT001Repository.queryAll()
                .map(ReceiveT001::getBukrs)
                .collect(Collectors.toSet());
        final Set<String> werkses = t001kRepository.queryAll()
                .filter(t001k -> bukrses.contains(t001k.getBukrs()))
                .map(T001k::getBwkey)
                .collect(Collectors.toSet());
        return em.createNamedQuery("T001l.queryAll", T001l.class)
                .getResultList()
                .stream()
                .filter(t001l -> werkses.contains(t001l.getWerks()));
    }

}
