package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.repository.Kna1Repository;
import com.hengyi.japp.cargo.domain.sap.Kna1;
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
public class JpaKna1Repository implements Kna1Repository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public Kna1 save(Kna1 t001) {
        return em.merge(t001);
    }

    @Override
    public Kna1 find(String id) {
        return em.find(Kna1.class, id);
    }

    @Override
    public Stream<Kna1> autocomplete(T001 t001, String q) {
        final TypedQuery<Kna1> query = Optional.ofNullable(t001)
                .map(it -> em.createNamedQuery("Kna1.autocompleteByT001", Kna1.class).setParameter("t001", t001))
                .orElseGet(() -> em.createNamedQuery("Kna1.autocomplete", Kna1.class));
        return query.setParameter("q", "%" + q + "%").setMaxResults(20).getResultList().stream();
    }

}
