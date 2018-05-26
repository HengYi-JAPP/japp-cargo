package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.google.common.collect.Sets;
import com.hengyi.japp.cargo.Util;
import com.hengyi.japp.cargo.application.query.OperatorQuery;
import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.OperatorPermission;
import com.hengyi.japp.cargo.domain.Operator_;
import com.hengyi.japp.cargo.domain.repository.OperatorPermissionRepository;
import com.hengyi.japp.cargo.domain.repository.OperatorRepository;
import com.hengyi.japp.cargo.domain.sap.T001;
import org.jzb.J;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaOperatorRepository implements OperatorRepository, Serializable {
    @Inject
    private EntityManager em;
    @Inject
    private OperatorPermissionRepository operatorPermissionRepository;

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
        if ("12000077".equals(operator.getHrId())) {
            operator.setAdmin(true);
        }
        return em.merge(operator);
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

    @Override
    public void query(OperatorQuery command) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Operator> resultCq = cb.createQuery(Operator.class).distinct(true);
        Root<Operator> root = resultCq.from(Operator.class);
        resultCq.select(root);
        resultCq.where(predicates(command, cb, root));
        if (command.pageSize == null) {
            command.result = em.createQuery(resultCq).getResultList().stream();
            return;
        }
        command.result = em.createQuery(resultCq)
                .setFirstResult(command.first)
                .setMaxResults(command.pageSize)
                .getResultList()
                .stream();
        if (J.isEmpty(command.defaultReceiveBukrsSet)) {
            CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
            root = countCq.from(Operator.class);
            countCq.select(cb.count(root));
            countCq.where(predicates(command, cb, root));
            Optional.ofNullable(command.pageSize)
                    .ifPresent(it -> {
                        command.count = em.createQuery(countCq).getSingleResult();
                    });
        } else {
            command.result = command.result.filter(operator -> Optional.ofNullable(operator)
                    .map(Operator::getId)
                    .map(id -> em.find(OperatorPermission.class, id))
                    .map(OperatorPermission::getDefaultReceiveT001)
                    .map(T001::getBukrs)
                    .filter(command.defaultReceiveBukrsSet::contains)
                    .isPresent()
            );
            Optional.ofNullable(command.pageSize)
                    .ifPresent(it -> {
                        final Collection<Operator> collect = command.result.collect(Collectors.toList());
                        command.result = collect.stream();
                        command.count = collect.size();
                    });
        }
    }

    private Predicate[] predicates(OperatorQuery command, CriteriaBuilder cb, Root<Operator> root) {
        final Set<Predicate> ps = Sets.newHashSet(cb.equal(root.get(Operator_.deleted), false));
        Optional.ofNullable(command.q)
                .map(J::nonBlank)
                .map(it -> cb.like(root.get(Operator_.name), "%" + command.q + "%"))
                .ifPresent(ps::add);
        return ps.toArray(new Predicate[ps.size()]);
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
