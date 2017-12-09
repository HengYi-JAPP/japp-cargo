package com.hengyi.japp.cargo.application.internal;

import com.hengyi.japp.cargo.application.ReceiveT001Service;
import com.hengyi.japp.cargo.application.command.EntityDTO;
import com.hengyi.japp.cargo.application.command.ReceiveT001AddCommand;
import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.config.ReceiveT001;
import com.hengyi.japp.cargo.domain.repository.*;
import com.hengyi.japp.cargo.domain.sap.Kna1;
import com.hengyi.japp.cargo.domain.sap.Lfa1;
import com.hengyi.japp.cargo.domain.sap.T001;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Principal;
import java.util.Optional;

/**
 * 描述：
 *
 * @author jzb 2017-12-02
 */
@Stateless
public class ReceiveT001ServiceImpl implements ReceiveT001Service {
    @Inject
    private T001Repository t001Repository;
    @Inject
    private Lfa1Repository lfa1Repository;
    @Inject
    private Kna1Repository kna1Repository;
    @Inject
    private ReceiveT001Repository receiveT001Repository;
    @Inject
    private OperatorRepository operatorRepository;

    @Override
    public ReceiveT001 add(Principal principal, ReceiveT001AddCommand command) {
        final T001 t001 = t001Repository.find(command.getT001().getId());
        final ReceiveT001 receiveT001 = Optional.ofNullable(receiveT001Repository.find(t001.getBukrs()))
                .orElse(new ReceiveT001());
        receiveT001.setDeleted(false);
        receiveT001.setT001(t001);
        final Kna1 kna1 = Optional.ofNullable(command.getKna1())
                .map(EntityDTO::getId)
                .map(kna1Repository::find)
                .orElse(null);
        receiveT001.setKna1(kna1);
        final Lfa1 lfa1 = Optional.ofNullable(command.getLfa1())
                .map(EntityDTO::getId)
                .map(lfa1Repository::find)
                .orElse(null);
        receiveT001.setLfa1(lfa1);
        final Operator operator = operatorRepository.find(principal);
        receiveT001._log(operator);
        return receiveT001Repository.save(receiveT001);
    }

    @Override
    public void remove(Principal principal, String id) {
        ReceiveT001 receiveT001 = receiveT001Repository.find(id);
        receiveT001.setDeleted(true);
        receiveT001Repository.save(receiveT001);
    }
}
