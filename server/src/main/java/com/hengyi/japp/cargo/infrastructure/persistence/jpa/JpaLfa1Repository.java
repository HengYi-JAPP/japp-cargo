package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.repository.Lfa1Repository;
import com.hengyi.japp.cargo.domain.sap.Lfa1;
import com.hengyi.japp.cargo.domain.sap.T001;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaLfa1Repository implements Lfa1Repository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public Lfa1 save(Lfa1 t001) {
        return em.merge(t001);
    }

    @Override
    public Lfa1 find(String id) {
        return em.find(Lfa1.class, id);
    }

    @Override
    public Stream<Lfa1> autocomplete(T001 t001, String q) {
        final TypedQuery<Lfa1> query = Optional.ofNullable(t001)
                .map(it -> em.createNamedQuery("Lfa1.autocompleteByT001", Lfa1.class).setParameter("t001", t001))
                .orElseGet(() -> em.createNamedQuery("Lfa1.autocomplete", Lfa1.class));
        return query.setParameter("q", "%" + q + "%").setMaxResults(20).getResultList().stream();
    }

}
