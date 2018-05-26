package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.google.common.collect.Sets;
import com.hengyi.japp.cargo.application.query.MegSendInfoQuery;
import com.hengyi.japp.cargo.domain.meg.MegReceiveInfo;
import com.hengyi.japp.cargo.domain.meg.MegSendInfo;
import com.hengyi.japp.cargo.domain.meg.MegSendInfo_;
import com.hengyi.japp.cargo.domain.repository.MegSendInfoRepository;
import org.jzb.J;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
public class JpaMegSendInfoRepository extends JpaCURDRepository<MegSendInfo, String> implements MegSendInfoRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public MegSendInfo save(MegSendInfo sendInfo) {
        final MegReceiveInfo receiveInfo = sendInfo.getReceiveInfo();
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
    public Stream<MegSendInfo> querySendInfo(Principal principal, LocalDate ld) {
        return em.createNamedQuery("MegSendInfo.querySendInfo", MegSendInfo.class)
                .setParameter("sendDate", J.date(ld))
                .setParameter("creatorId", principal.getName())
                .getResultList()
                .stream();
    }

    @Override
    public void query(MegSendInfoQuery command) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MegSendInfo> resultCq = cb.createQuery(MegSendInfo.class).distinct(true);
        Root<MegSendInfo> root = resultCq.from(MegSendInfo.class);
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
        root = countCq.from(MegSendInfo.class);
        countCq.select(cb.count(root));
        countCq.where(predicates(command, cb, root));
        command.count = em.createQuery(countCq).getSingleResult();
    }

    private Predicate[] predicates(MegSendInfoQuery command, CriteriaBuilder cb, Root<MegSendInfo> root) {
        final Set<Predicate> ps = Sets.newHashSet(cb.equal(root.get(MegSendInfo_.deleted), false));
        Optional.ofNullable(command.ld)
                .map(J::date)
                .map(it -> cb.equal(root.get(MegSendInfo_.sendDate), it))
                .ifPresent(ps::add);
        Optional.ofNullable(command.ldStart)
                .map(J::date)
                .map(it -> cb.greaterThanOrEqualTo(root.get(MegSendInfo_.sendDate), it))
                .ifPresent(ps::add);
        Optional.ofNullable(command.ldEnd)
                .map(J::date)
                .map(it -> cb.lessThanOrEqualTo(root.get(MegSendInfo_.sendDate), it))
                .ifPresent(ps::add);
        return ps.toArray(new Predicate[ps.size()]);
    }
}
