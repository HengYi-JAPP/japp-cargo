package com.hengyi.japp.cargo.application.internal;

import com.hengyi.japp.cargo.application.SupplyInfoService;
import com.hengyi.japp.cargo.application.command.EntityDTO;
import com.hengyi.japp.cargo.application.command.SupplyInfoUpdateCommand;
import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.config.SupplyInfo;
import com.hengyi.japp.cargo.domain.repository.OperatorRepository;
import com.hengyi.japp.cargo.domain.repository.SupplyInfoRepository;
import com.hengyi.japp.cargo.domain.repository.T001Repository;
import com.hengyi.japp.cargo.domain.sap.T001;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author jzb 2017-12-02
 */
@Stateless
public class SupplyInfoServiceImpl implements SupplyInfoService {
    @Inject
    private T001Repository t001Repository;
    @Inject
    private OperatorRepository operatorRepository;
    @Inject
    private SupplyInfoRepository supplyInfoRepository;

    @Override
    public SupplyInfo create(Principal principal, SupplyInfoUpdateCommand command) {
        return save(principal, new SupplyInfo(), command);
    }

    private SupplyInfo save(Principal principal, SupplyInfo o, SupplyInfoUpdateCommand command) {
        o.setDeleted(false);
        o.setName(command.getName());
        final Set<T001> t001s = command.getT001s()
                .stream()
                .map(EntityDTO::getId)
                .map(t001Repository::find)
                .collect(Collectors.toSet());
        o.setT001s(t001s);
        final Operator operator = operatorRepository.find(principal);
        o._log(operator);
        return supplyInfoRepository.save(o);
    }

    @Override
    public SupplyInfo update(Principal principal, String id, SupplyInfoUpdateCommand command) {
        return save(principal, supplyInfoRepository.find(id), command);
    }

    @Override
    public void delete(Principal principal, String id) {
        final SupplyInfo transCorp = supplyInfoRepository.find(id);
        transCorp.setDeleted(true);
        supplyInfoRepository.save(transCorp);
    }
}
