package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.config.TransCorp;
import com.hengyi.japp.cargo.domain.repository.TransCorpRepository;
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
public class JpaTransCorpRepository implements TransCorpRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public TransCorp save(TransCorp transCorp) {
        if (J.isBlank(transCorp.getId())) {
            transCorp.setId(J.uuid58());
        }
        return em.merge(transCorp);
    }

    @Override
    public TransCorp find(String id) {
        return em.find(TransCorp.class, id);
    }

    @Override
    public Stream<TransCorp> queryAll() {
        return em.createNamedQuery("TransCorp.queryAll", TransCorp.class).getResultList().stream();
    }

    @Override
    public Stream<TransCorp> queryBy(T001 t001) {
        return em.createNamedQuery("TransCorp.queryByT001", TransCorp.class).setParameter("t001", t001).getResultList().stream();
    }

}
