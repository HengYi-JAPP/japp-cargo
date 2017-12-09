package com.hengyi.japp.cargo.application;

import com.hengyi.japp.cargo.application.command.PtaSendInfoUpdateCommand;
import com.hengyi.japp.cargo.domain.pta.PtaSendInfo;

import java.security.Principal;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
public interface PtaService {
    PtaSendInfo create(Principal principal, String ylipsId, PtaSendInfoUpdateCommand command) throws Exception;

    PtaSendInfo update(Principal principal, String ylipsId, String id, PtaSendInfoUpdateCommand command) throws Exception;

    void delete(Principal principal, String id) throws Exception;
}
