package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.google.common.collect.Sets;
import com.hengyi.japp.cargo.Util;
import com.hengyi.japp.cargo.application.query.PtaSendInfoQuery;
import com.hengyi.japp.cargo.domain.pta.PtaReceiveInfo;
import com.hengyi.japp.cargo.domain.pta.PtaSendInfo;
import com.hengyi.japp.cargo.domain.pta.PtaSendInfo_;
import com.hengyi.japp.cargo.domain.repository.PtaSendInfoRepository;
import com.hengyi.japp.cargo.domain.sap.Ylips;
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
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaPtaSendInfoRepository extends JpaCURDRepository<PtaSendInfo, String> implements PtaSendInfoRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public PtaSendInfo save(PtaSendInfo sendInfo) {
        final PtaReceiveInfo receiveInfo = sendInfo.getReceiveInfo();
        if (J.isBlank(sendInfo.getId())) {
            final String id = J.uuid58();
            sendInfo.setId(id);
        }
        receiveInfo.setId(sendInfo.getId());
        J.emptyIfNull(receiveInfo.getSapReceiveInfos())
                .forEach(it -> {
                    if (J.isBlank(it.getId())) {
                        it.setId(J.uuid58());
                    }
                });
        em.merge(receiveInfo);
        return em.merge(sendInfo);
    }

    @Override
    public PtaSendInfo queryBy(Ylips ylips) {
        final TypedQuery<PtaSendInfo> query = em.createNamedQuery("PtaSendInfo.queryByYlips", PtaSendInfo.class)
                .setParameter("ylips", ylips);
        return Util.getSingle(query);
    }

    @Override
    public Stream<PtaSendInfo> querySendInfo(Principal principal, LocalDate ld) {
        return em.createNamedQuery("PtaSendInfo.querySendInfo", PtaSendInfo.class)
                .setParameter("sendDate", J.date(ld))
                .setParameter("creatorId", principal.getName())
                .getResultList()
                .stream();
    }

    @Override
    public void query(PtaSendInfoQuery command) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<PtaSendInfo> resultCq = cb.createQuery(PtaSendInfo.class).distinct(true);
        Root<PtaSendInfo> root = resultCq.from(PtaSendInfo.class);
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
        CriteriaQuery<Long> countCq = cb.createQuery(Long.class);
        root = countCq.from(PtaSendInfo.class);
        countCq.select(cb.count(root));
        countCq.where(predicates(command, cb, root));
        command.count = em.createQuery(countCq).getSingleResult();
    }

    private Predicate[] predicates(PtaSendInfoQuery command, CriteriaBuilder cb, Root<PtaSendInfo> root) {
        final Set<Predicate> ps = Sets.newHashSet(cb.equal(root.get(PtaSendInfo_.deleted), false));

        Optional.ofNullable(command.ld)
                .map(J::date)
                .map(it -> cb.equal(root.get(PtaSendInfo_.sendDate), it))
                .ifPresent(ps::add);
        Optional.ofNullable(command.ldStart)
                .map(J::date)
                .map(it -> cb.greaterThanOrEqualTo(root.get(PtaSendInfo_.sendDate), it))
                .ifPresent(ps::add);
        Optional.ofNullable(command.ldEnd)
                .map(J::date)
                .map(it -> cb.lessThanOrEqualTo(root.get(PtaSendInfo_.sendDate), it))
                .ifPresent(ps::add);
        Optional.ofNullable(command.packTypes)
                .filter(J::nonEmpty)
                .map(it -> root.get(PtaSendInfo_.packType).in(it))
                .ifPresent(ps::add);
        Optional.ofNullable(command.transTypes)
                .filter(J::nonEmpty)
                .map(it -> root.get(PtaSendInfo_.transType).in(it))
                .ifPresent(ps::add);
        return ps.toArray(new Predicate[ps.size()]);
    }
}
