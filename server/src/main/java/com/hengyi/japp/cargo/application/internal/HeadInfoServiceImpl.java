package com.hengyi.japp.cargo.application.internal;

import com.hengyi.japp.cargo.application.HeadInfoService;
import com.hengyi.japp.cargo.application.command.EntityDTO;
import com.hengyi.japp.cargo.application.command.HeadInfoUpdateCommand;
import com.hengyi.japp.cargo.application.command.TransCorpUpdateCommand;
import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.config.HeadInfo;
import com.hengyi.japp.cargo.domain.config.TransCorp;
import com.hengyi.japp.cargo.domain.repository.HeadInfoRepository;
import com.hengyi.japp.cargo.domain.repository.OperatorRepository;
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
public class HeadInfoServiceImpl implements HeadInfoService {
    @Inject
    private T001Repository t001Repository;
    @Inject
    private OperatorRepository operatorRepository;
    @Inject
    private HeadInfoRepository headInfoRepository;

    @Override
    public HeadInfo create(Principal principal, HeadInfoUpdateCommand command) {
        return save(principal, new HeadInfo(), command);
    }

    private HeadInfo save(Principal principal, HeadInfo o, HeadInfoUpdateCommand command) {
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
        return headInfoRepository.save(o);
    }

    @Override
    public HeadInfo update(Principal principal, String id, HeadInfoUpdateCommand command) {
        return save(principal, headInfoRepository.find(id), command);
    }

    @Override
    public void delete(Principal principal, String id) {
        final HeadInfo transCorp = headInfoRepository.find(id);
        transCorp.setDeleted(true);
        headInfoRepository.save(transCorp);
    }
}
