package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.Util;
import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.repository.OperatorRepository;
import org.jzb.J;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.security.Principal;
import java.util.stream.Stream;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaOperatorRepository implements OperatorRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public Operator find(String id) {
        return em.find(Operator.class, id);
    }

    @Override
    public Operator find(Principal principal) {
        return find(principal.getName());
    }

    @Override
    public Operator save(Operator operator) {
        if (J.isBlank(operator.getId())) {
            operator.setId(J.uuid58());
        }
        return em.merge(operator);
    }

    @Override
    public long count() {
        return em.createNamedQuery("Operator.count", Long.class).getSingleResult();
    }

    @Override
    public Stream<Operator> queryAll(int first, int pageSize) {
        return em.createNamedQuery("Operator.queryAll", Operator.class)
                .setFirstResult(first)
                .setMaxResults(pageSize)
                .getResultList()
                .stream();
    }

    @Override
    public Operator findByHrIdOrOaId(String id) {
        Operator result = findByHrId(id);
        if (result != null)
            return result;
        result = findByOaId(id);
        if (result != null)
            return result;
        return null;
    }

    private Operator findByHrId(String casPrincipal) {
        TypedQuery<Operator> typedQuery = em.createNamedQuery("Operator.findByHrId", Operator.class)
                .setParameter("hrId", casPrincipal)
                .setMaxResults(1);
        return Util.getSingle(typedQuery);
    }

    private Operator findByOaId(String casPrincipal) {
        TypedQuery<Operator> typedQuery = em.createNamedQuery("Operator.findByOaId", Operator.class)
                .setParameter("oaId", casPrincipal)
                .setMaxResults(1);
        return Util.getSingle(typedQuery);
    }

}
