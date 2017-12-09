package com.hengyi.japp.cargo.infrastructure.persistence.jpa;

import com.hengyi.japp.cargo.domain.AbstractSendInfoEntity;
import com.hengyi.japp.cargo.domain.CarNoInfoLog;
import com.hengyi.japp.cargo.domain.repository.CarNoInfoLogRepository;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by jzb on 16-10-28.
 */
@ApplicationScoped
public class JpaCarNoInfoLogRepository implements CarNoInfoLogRepository, Serializable {
    @Inject
    private EntityManager em;

    @Override
    public CarNoInfoLog save(AbstractSendInfoEntity sendInfo) {
        return Optional.ofNullable(sendInfo.getCarNo())
                .map(carNo -> {
                    CarNoInfoLog carNoInfoLog = new CarNoInfoLog();
                    carNoInfoLog.setCarNo(carNo);
                    carNoInfoLog.setCarDriver(sendInfo.getCarDriver());
                    carNoInfoLog.setTransCorp(sendInfo.getTransCorp());
                    return save(carNoInfoLog);
                })
                .orElse(null);
    }

    @Override
    public CarNoInfoLog save(CarNoInfoLog t001) {
        return em.merge(t001);
    }

    @Override
    public CarNoInfoLog find(String id) {
        return em.find(CarNoInfoLog.class, id);
    }

    @Override
    public Stream<CarNoInfoLog> autocomplete(String q) {
        return em.createNamedQuery("CarNoInfoLog.autocomplete", CarNoInfoLog.class)
                .setParameter("q", "%" + StringUtils.upperCase(q) + "%")
                .setMaxResults(20)
                .getResultList().stream();
    }
}
