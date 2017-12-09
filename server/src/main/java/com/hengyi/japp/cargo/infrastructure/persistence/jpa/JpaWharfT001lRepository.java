package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.config.WharfT001l;
import com.hengyi.japp.cargo.domain.repository.T001kRepository;
import com.hengyi.japp.cargo.domain.repository.WharfT001lRepository;
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
public class JpaWharfT001lRepository implements WharfT001lRepository, Serializable {
    @Inject
    private EntityManager em;
    @Inject
    private T001kRepository t001kRepository;

    @Override
    public WharfT001l find(T001lPK pk) {
        return em.find(WharfT001l.class, pk);
    }

    @Override
    public Stream<WharfT001l> queryAll() {
        return em.createNamedQuery("WharfT001l.queryAll", WharfT001l.class)
                .getResultList()
                .stream();
    }

    @Override
    public Stream<WharfT001l> queryBy(T001 t001) {
        final Set<String> werkses = t001kRepository.queryAll()
                .filter(t001k -> t001k.getBukrs().equals(t001.getBukrs()))
                .map(T001k::getBwkey)
                .collect(Collectors.toSet());
        return queryAll().filter(receiveT001l -> werkses.contains(receiveT001l.getWerks()));
    }

    @Override
    public WharfT001l save(WharfT001l wharfT001l) {
        if (J.isBlank(wharfT001l.getWerks())) {
            wharfT001l.setWerks(wharfT001l.getT001l().getWerks());
        }
        if (J.isBlank(wharfT001l.getLgort())) {
            wharfT001l.setLgort(wharfT001l.getT001l().getLgort());
        }
        return em.merge(wharfT001l);
    }

}
