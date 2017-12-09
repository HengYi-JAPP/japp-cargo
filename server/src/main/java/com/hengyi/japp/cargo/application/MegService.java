package com.hengyi.japp.cargo.application;

import com.hengyi.japp.cargo.application.command.MegSendInfoUpdateCommand;
import com.hengyi.japp.cargo.domain.meg.MegSendInfo;

import java.security.Principal;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
public interface MegService {
    MegSendInfo create(Principal principal, MegSendInfoUpdateCommand command) throws Exception;

    MegSendInfo update(Principal principal, String id, MegSendInfoUpdateCommand command) throws Exception;

    void delete(Principal principal, String id) throws Exception;
}
