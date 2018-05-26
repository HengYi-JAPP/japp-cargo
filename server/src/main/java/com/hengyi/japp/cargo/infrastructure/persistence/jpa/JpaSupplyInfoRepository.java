package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.config.SupplyInfo;
import com.hengyi.japp.cargo.domain.repository.SupplyInfoRepository;
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
public class JpaSupplyInfoRepository implements SupplyInfoRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public SupplyInfo save(SupplyInfo transCorp) {
        if (J.isBlank(transCorp.getId())) {
            transCorp.setId(J.uuid58());
        }
        return em.merge(transCorp);
    }

    @Override
    public SupplyInfo find(String id) {
        return em.find(SupplyInfo.class, id);
    }

    @Override
    public Stream<SupplyInfo> queryAll() {
        return em.createNamedQuery("SupplyInfo.queryAll", SupplyInfo.class).getResultList().stream();
    }

    @Override
    public Stream<SupplyInfo> queryBy(T001 t001) {
        return em.createNamedQuery("SupplyInfo.queryByT001", SupplyInfo.class).setParameter("t001", t001).getResultList().stream();
    }

}
