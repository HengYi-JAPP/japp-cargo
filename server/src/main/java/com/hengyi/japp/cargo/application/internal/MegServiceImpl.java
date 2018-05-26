package com.hengyi.japp.cargo.application.internal;

import com.hengyi.japp.cargo.application.ApplicationEvents;
import com.hengyi.japp.cargo.application.MegService;
import com.hengyi.japp.cargo.application.command.EntityDTO;
import com.hengyi.japp.cargo.application.command.MegSendInfoUpdateCommand;
import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.config.HeadInfo;
import com.hengyi.japp.cargo.domain.config.SupplyInfo;
import com.hengyi.japp.cargo.domain.config.TransCorp;
import com.hengyi.japp.cargo.domain.meg.MegReceiveInfo;
import com.hengyi.japp.cargo.domain.meg.MegSapReceiveInfo;
import com.hengyi.japp.cargo.domain.meg.MegSendInfo;
import com.hengyi.japp.cargo.domain.repository.*;
import com.hengyi.japp.cargo.domain.sap.Lfa1;
import com.hengyi.japp.cargo.domain.sap.T001;
import com.hengyi.japp.cargo.domain.sap.T001l;
import com.hengyi.japp.cargo.domain.sap.T001w;
import org.jzb.J;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
@Stateless
public class MegServiceImpl implements MegService {
    @Inject
    private MegSendInfoRepository megSendInfoRepository;
    @Inject
    private TransCorpRepository transCorpRepository;
    @Inject
    private T001lRepository t001lRepository;
    @Inject
    private T001Repository t001Repository;
    @Inject
    private OperatorRepository operatorRepository;
    @Inject
    private Lfa1Repository lfa1Repository;
    @Inject
    private T001wRepository t001wRepository;
    @Inject
    private HeadInfoRepository headInfoRepository;
    @Inject
    private SupplyInfoRepository supplyInfoRepository;
    @Inject
    private ApplicationEvents applicationEvents;

    @Override
    public MegSendInfo create(Principal principal, MegSendInfoUpdateCommand command) throws Exception {
        final MegSendInfo result = save(principal, new MegSendInfo(), command);
        applicationEvents.fireCreate(MegSendInfo.class, principal, result.getId(), command);
        return result;
    }

    private MegSendInfo save(Principal principal, MegSendInfo sendInfo, MegSendInfoUpdateCommand command) {
        SupplyInfo supplyInfo = Optional.ofNullable(command.getHeadInfo())
                .map(EntityDTO::getId)
                .map(supplyInfoRepository::find)
                .orElse(null);
        sendInfo.setSupplyInfo(supplyInfo);
        HeadInfo headInfo = Optional.ofNullable(command.getHeadInfo())
                .map(EntityDTO::getId)
                .map(headInfoRepository::find)
                .orElse(null);
        sendInfo.setHeadInfo(headInfo);
        Lfa1 lfa1 = Optional.ofNullable(command.getLfa1())
                .map(EntityDTO::getId)
                .map(lfa1Repository::find)
                .orElse(null);
        sendInfo.setLfa1(lfa1);
        final TransCorp transCorp = Optional.ofNullable(command.getTransCorp())
                .map(EntityDTO::getId)
                .map(transCorpRepository::find)
                .orElse(null);
        sendInfo.setMegType(command.getMegType());
        final T001l wharf = Optional.ofNullable(command.getWharf())
                .map(t001lRepository::find)
                .orElse(null);
        sendInfo.setWharf(wharf);
        sendInfo.setTransCorp(transCorp);
        sendInfo.setCarNo(command.getCarNo());
        sendInfo.setCarDriver(command.getCarDriver());
        sendInfo.setSendDate(command.getSendDate());
        sendInfo.setLfimg2(command.getSendLfimg2());
        sendInfo.setLfimg1(command.getSendLfimg1());
        sendInfo.setLfimg(command.getSendLfimg2().add(command.getSendLfimg1().negate()));
        sendInfo.setNote(command.getSendNote());

        MegReceiveInfo receiveInfo = new MegReceiveInfo();
        sendInfo.setReceiveInfo(receiveInfo);
        final T001l t001l = t001lRepository.find(command.getReceiveT001l());
        receiveInfo.setT001l(t001l);
        final T001w t001w = t001wRepository.find(t001l);
        receiveInfo.setT001w(t001w);
        final T001 t001 = t001Repository.find(t001w);
        receiveInfo.setT001(t001);
        receiveInfo.setReceiveDate(command.getReceiveDate());
        receiveInfo.setLfimg2(command.getReceiveLfimg2());
        receiveInfo.setLfimg1(command.getReceiveLfimg1());
        receiveInfo.setLfimg(command.getReceiveLfimg2().add(command.getReceiveLfimg1().negate()));
        receiveInfo.setDiffLfimg1(command.getDiffLfimg1());
        receiveInfo.setDiffLfimg2(command.getDiffLfimg2());
        receiveInfo.setNote(command.getReceiveNote());
        receiveInfo.setPickPoundNo(command.getPickPoundNo());
        final Set<MegSapReceiveInfo> sapReceiveInfos = J.emptyIfNull(command.getSapReceiveInfos())
                .stream()
                .map(dto -> {
                    MegSapReceiveInfo sapReceiveInfo = new MegSapReceiveInfo();
                    sapReceiveInfo.setId(J.uuid58());
                    sapReceiveInfo.setReceiveInfo(receiveInfo);
                    sapReceiveInfo.setSapNo(dto.getSapNo());
                    sapReceiveInfo.setAmount(dto.getAmount());
                    final T001l sapT001l = Optional.ofNullable(dto.getT001l())
                            .map(t001lRepository::find)
                            .orElse(null);
                    sapReceiveInfo.setT001l(sapT001l);
                    return sapReceiveInfo;
                })
                .collect(Collectors.toSet());
        receiveInfo.setSapReceiveInfos(sapReceiveInfos);

        final Operator operator = operatorRepository.find(principal);
        sendInfo._log(operator);
        return megSendInfoRepository.save(sendInfo);
    }

    @Override
    public MegSendInfo update(Principal principal, String id, MegSendInfoUpdateCommand command) throws Exception {
        final MegSendInfo megSendInfo = megSendInfoRepository.find(id);
        final MegSendInfo result = save(principal, megSendInfo, command);
        applicationEvents.fireUpdate(MegSendInfo.class, principal, id, command);
        return result;
    }

    @Override
    public void delete(Principal principal, String id) throws Exception {
        final MegSendInfo sendInfo = megSendInfoRepository.find(id);
        sendInfo.setDeleted(true);
        megSendInfoRepository.save(sendInfo);
        applicationEvents.fireDelete(MegSendInfo.class, principal, id);
    }
}
