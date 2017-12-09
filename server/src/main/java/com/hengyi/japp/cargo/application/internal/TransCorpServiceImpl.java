package com.hengyi.japp.cargo.application.internal;

import com.hengyi.japp.cargo.application.TransCorpService;
import com.hengyi.japp.cargo.application.command.EntityDTO;
import com.hengyi.japp.cargo.application.command.TransCorpUpdateCommand;
import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.config.TransCorp;
import com.hengyi.japp.cargo.domain.repository.OperatorRepository;
import com.hengyi.japp.cargo.domain.repository.T001Repository;
import com.hengyi.japp.cargo.domain.repository.TransCorpRepository;
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
public class TransCorpServiceImpl implements TransCorpService {
    @Inject
    private T001Repository t001Repository;
    @Inject
    private OperatorRepository operatorRepository;
    @Inject
    private TransCorpRepository transCorpRepository;

    @Override
    public TransCorp create(Principal principal, TransCorpUpdateCommand command) {
        return save(principal, new TransCorp(), command);
    }

    private TransCorp save(Principal principal, TransCorp transCorp, TransCorpUpdateCommand command) {
        transCorp.setDeleted(false);
        transCorp.setName(command.getName());
        final Set<T001> t001s = command.getT001s()
                .stream()
                .map(EntityDTO::getId)
                .map(t001Repository::find)
                .collect(Collectors.toSet());
        transCorp.setT001s(t001s);
        final Operator operator = operatorRepository.find(principal);
        transCorp._log(operator);
        return transCorpRepository.save(transCorp);
    }

    @Override
    public TransCorp update(Principal principal, String id, TransCorpUpdateCommand command) {
        return save(principal, transCorpRepository.find(id), command);
    }

    @Override
    public void delete(Principal principal, String id) {
        final TransCorp transCorp = transCorpRepository.find(id);
        transCorp.setDeleted(true);
        transCorpRepository.save(transCorp);
    }
}
