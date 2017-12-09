package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.config.ReceiveT001l;
import com.hengyi.japp.cargo.domain.repository.ReceiveT001lRepository;
import com.hengyi.japp.cargo.domain.repository.T001kRepository;
import com.hengyi.japp.cargo.domain.sap.T001;
import com.hengyi.japp.cargo.domain.sap.T001k;
import com.hengyi.japp.cargo.domain.sap.T001lPK;
import org.jzb.J;

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
public class JpaReceiveT001lRepository implements ReceiveT001lRepository, Serializable {
    @Inject
    private EntityManager em;
    @Inject
    private T001kRepository t001kRepository;

    @Override
    public ReceiveT001l save(ReceiveT001l t001l) {
        if (J.isBlank(t001l.getWerks())) {
            t001l.setWerks(t001l.getT001l().getWerks());
        }
        if (J.isBlank(t001l.getLgort())) {
            t001l.setLgort(t001l.getT001l().getLgort());
        }
        return em.merge(t001l);
    }

    @Override
    public ReceiveT001l find(T001lPK pk) {
        return em.find(ReceiveT001l.class, pk);
    }

    @Override
    public Stream<ReceiveT001l> queryAll() {
        return em.createNamedQuery("ReceiveT001l.queryAll", ReceiveT001l.class)
                .getResultList()
                .stream();
    }

    @Override
    public Stream<ReceiveT001l> queryBy(T001 t001) {
        final Set<String> werkses = t001kRepository.queryAll()
                .filter(t001k -> t001k.getBukrs().equals(t001.getBukrs()))
                .map(T001k::getBwkey)
                .collect(Collectors.toSet());
        return queryAll().filter(receiveT001l -> werkses.contains(receiveT001l.getWerks()));
    }

}
