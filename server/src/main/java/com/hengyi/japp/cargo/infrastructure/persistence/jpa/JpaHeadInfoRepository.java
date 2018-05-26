package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.config.HeadInfo;
import com.hengyi.japp.cargo.domain.repository.HeadInfoRepository;
import com.hengyi.japp.cargo.domain.sap.T001;
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
public class JpaHeadInfoRepository implements HeadInfoRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public HeadInfo save(HeadInfo transCorp) {
        if (J.isBlank(transCorp.getId())) {
            transCorp.setId(J.uuid58());
        }
        return em.merge(transCorp);
    }

    @Override
    public HeadInfo find(String id) {
        return em.find(HeadInfo.class, id);
    }

    @Override
    public Stream<HeadInfo> queryAll() {
        return em.createNamedQuery("HeadInfo.queryAll", HeadInfo.class).getResultList().stream();
    }

    @Override
    public Stream<HeadInfo> queryBy(T001 t001) {
        return em.createNamedQuery("HeadInfo.queryByT001", HeadInfo.class).setParameter("t001", t001).getResultList().stream();
    }

}
