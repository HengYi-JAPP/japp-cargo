package com.hengyi.japp.cargo.interfaces;

import com.google.common.collect.Sets;
import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.config.ReceiveT001;
import com.hengyi.japp.cargo.domain.config.ReceiveT001l;
import com.hengyi.japp.cargo.domain.config.WharfT001l;
import com.hengyi.japp.cargo.domain.repository.*;
import com.hengyi.japp.cargo.domain.sap.T001lPK;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Optional;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
@Singleton
@Startup
public class Fix {
    @Inject
    OperatorRepository operatorRepository;
    @Inject
    T001Repository t001Repository;
    @Inject
    ReceiveT001Repository receiveT001Repository;
    @Inject
    T001lRepository t001lRepository;
    @Inject
    ReceiveT001lRepository receiveT001lRepository;
    @Inject
    WharfT001lRepository wharfT001lRepository;

    @PostConstruct
    void PostConstruct() {
        Operator operator = Optional.ofNullable(operatorRepository.findByHrIdOrOaId("12000077"))
                .orElseGet(() -> {
                    Operator o = new Operator();
                    o.setAdmin(true);
                    o.setHrId("12000077");
                    o.setOaId("jzb");
                    o.setName("金赵波");
                    return operatorRepository.save(o);
                });

        // 收货公司
        Sets.newHashSet("1000", "2000", "3000", "9100", "9200", "9300")
                .stream()
                .map(t001Repository::find)
                .filter(Objects::nonNull)
                .forEach(t001 -> {
                    ReceiveT001 receiveT001 = new ReceiveT001();
                    receiveT001.setT001(t001);
                    receiveT001.setBukrs(t001.getBukrs());
                    receiveT001._log(operator);
                    receiveT001Repository.save(receiveT001);
                });
        // 卸货地
        Sets.newHashSet(
                new T001lPK("3100", "3151"),
                new T001lPK("3100", "3152"),
                new T001lPK("3100", "3104"),
                new T001lPK("3100", "3105"),
                new T001lPK("3100", "3941"),
                new T001lPK("3100", "3942"),
                new T001lPK("9210", "9001"),
                new T001lPK("9210", "9002"),
                new T001lPK("9210", "9011"),
                new T001lPK("9210", "9012"),
                new T001lPK("9310", "9315"),
                new T001lPK("9110", "9101")
        ).stream()
                .map(t001lRepository::find)
                .filter(Objects::nonNull)
                .forEach(t001l -> {
                    ReceiveT001l receiveT001l = new ReceiveT001l();
                    receiveT001l.setT001l(t001l);
                    receiveT001l.setWerks(t001l.getWerks());
                    receiveT001l.setLgort(t001l.getLgort());
                    receiveT001l._log(operator);
                    receiveT001lRepository.save(receiveT001l);
                });
        // 码头外库
        Sets.newHashSet(
                new T001lPK("3100", "3102"),
                new T001lPK("3100", "3103"),
                new T001lPK("3100", "3143"),
                new T001lPK("3100", "3144"),
                new T001lPK("3100", "3145"),
                new T001lPK("3100", "3146"),
                new T001lPK("3100", "3147"),
                new T001lPK("3100", "3166"),
                new T001lPK("3100", "3193"),
                new T001lPK("3100", "3194"),
                new T001lPK("3100", "3195"),
                new T001lPK("3100", "3196"),
                new T001lPK("9210", "9181"),
                new T001lPK("9210", "9182"),
                new T001lPK("9210", "9183"),
                new T001lPK("9210", "9184"),
                new T001lPK("9210", "9185"),
                new T001lPK("9210", "9186"),
                new T001lPK("9210", "9187"),
                new T001lPK("9310", "9385"),
                new T001lPK("9310", "9386"),
                new T001lPK("9310", "9387"),
                new T001lPK("9110", "9181"),
                new T001lPK("9110", "9182"),
                new T001lPK("9110", "9183"),
                new T001lPK("9110", "9184"),
                new T001lPK("9110", "9185"),
                new T001lPK("9110", "9186"),
                new T001lPK("9110", "9187")
        ).stream()
                .map(t001lRepository::find)
                .filter(Objects::nonNull)
                .forEach(t001l -> {
                    WharfT001l wharfT001l = new WharfT001l();
                    wharfT001l.setT001l(t001l);
                    wharfT001l.setWerks(t001l.getWerks());
                    wharfT001l.setLgort(t001l.getLgort());
                    wharfT001l._log(operator);
                    wharfT001lRepository.save(wharfT001l);
                });
    }
}
