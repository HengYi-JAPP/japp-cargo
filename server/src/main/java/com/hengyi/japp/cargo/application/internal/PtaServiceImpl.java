package com.hengyi.japp.cargo.application.internal;

import com.hengyi.japp.cargo.application.ApplicationEvents;
import com.hengyi.japp.cargo.application.PtaService;
import com.hengyi.japp.cargo.application.command.EntityDTO;
import com.hengyi.japp.cargo.application.command.PtaSendInfoUpdateCommand;
import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.config.TransCorp;
import com.hengyi.japp.cargo.domain.pta.PtaReceiveInfo;
import com.hengyi.japp.cargo.domain.pta.PtaSapReceiveInfo;
import com.hengyi.japp.cargo.domain.pta.PtaSendInfo;
import com.hengyi.japp.cargo.domain.repository.*;
import com.hengyi.japp.cargo.domain.sap.*;
import com.hengyi.japp.cargo.interfaces.sap.SapService;
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
public class PtaServiceImpl implements PtaService {
    @Inject
    private PtaSendInfoRepository ptaSendInfoRepository;
    @Inject
    private SapService sapService;
    @Inject
    private YlipsRepository ylipsRepository;
    @Inject
    private Lfa1Repository lfa1Repository;
    @Inject
    private T001Repository t001Repository;
    @Inject
    private T001lRepository t001lRepository;
    @Inject
    private T001wRepository t001wRepository;
    @Inject
    private OperatorRepository operatorRepository;
    @Inject
    private TransCorpRepository transCorpRepository;
    @Inject
    private ApplicationEvents applicationEvents;

    @Override
    public PtaSendInfo create(Principal principal, String ylipsId, PtaSendInfoUpdateCommand command) throws Exception {
        final PtaSendInfo result = save(principal, ylipsId, new PtaSendInfo(), command);
        applicationEvents.fireCreate(PtaSendInfo.class, principal, result.getId(), command);
        return result;
    }

    private PtaSendInfo save(Principal principal, String ylipsId, PtaSendInfo sendInfo, PtaSendInfoUpdateCommand command) throws Exception {
        Lfa1 lfa1 = Optional.ofNullable(command.getLfa1())
                .map(EntityDTO::getId)
                .map(lfa1Repository::find)
                .orElse(null);
        sendInfo.setLfa1(lfa1);
        sendInfo.setPackType(command.getPackType());
        sendInfo.setTransType(command.getTransType());
        final TransCorp transCorp = Optional.ofNullable(command.getTransCorp())
                .map(EntityDTO::getId)
                .map(transCorpRepository::find)
                .orElse(null);
        sendInfo.setTransCorp(transCorp);
        sendInfo.setBatchNo(command.getBatchNo());
        sendInfo.setCarNo(command.getCarNo());
        sendInfo.setCarDriver(command.getCarDriver());
        sendInfo.setPackNo(command.getPackNo());
        sendInfo.setSendDate(command.getSendDate());
        sendInfo.setLfimg2(command.getSendLfimg2());
        sendInfo.setLfimg1(command.getSendLfimg1());
        sendInfo.setLfimg(command.getSendLfimg2().add(command.getSendLfimg1().negate()));
        sendInfo.setNote(command.getSendNote());
        if (J.nonBlank(ylipsId)) {
            lfa1 = lfa1Repository.find("0000011002");
            sendInfo.setLfa1(lfa1);
            final Ylips ylips = Optional.ofNullable(ylipsRepository.find(ylipsId))
                    .orElseGet(() -> {
                        try {
                            final Ylips o = sapService.findYlips(ylipsId);
                            return ylipsRepository.save(o);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
            sendInfo.setYlips(ylips);
            sendInfo.setSendDate(ylips.getErdat());
            sendInfo.setCarNo(ylips.getCarNo());
            sendInfo.setPackType(ylips.getPackType());
            sendInfo.setTransType(ylips.getTransType());
            sendInfo.setBatchNo(ylips.getBatchNo());
        }

        PtaReceiveInfo receiveInfo = new PtaReceiveInfo();
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
        receiveInfo.setLittleLfimgPerPack(command.getLittleLfimgPerPack());
        receiveInfo.setNote(command.getReceiveNote());
        receiveInfo.setPoundNo(command.getPoundNo());
        final Set<PtaSapReceiveInfo> sapReceiveInfos = J.emptyIfNull(command.getSapReceiveInfos())
                .stream()
                .map(dto -> {
                    PtaSapReceiveInfo sapReceiveInfo = new PtaSapReceiveInfo();
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
        return ptaSendInfoRepository.save(sendInfo);
    }

    @Override
    public PtaSendInfo update(Principal principal, String ylipsId, String id, PtaSendInfoUpdateCommand command) throws Exception {
        final PtaSendInfo result = save(principal, ylipsId, ptaSendInfoRepository.find(id), command);
        applicationEvents.fireUpdate(PtaSendInfo.class, principal, id, command);
        return result;
    }

    @Override
    public void delete(Principal principal, String id) throws Exception {
        final PtaSendInfo sendInfo = ptaSendInfoRepository.find(id);
        sendInfo.setDeleted(true);
        ptaSendInfoRepository.save(sendInfo);
        applicationEvents.fireDelete(PtaSendInfo.class, principal, id);
    }
}
