package com.hengyi.japp.cargo.application;

import com.hengyi.japp.cargo.application.command.SupplyInfoUpdateCommand;
import com.hengyi.japp.cargo.domain.config.SupplyInfo;

import java.security.Principal;

public interface SupplyInfoService {
    SupplyInfo create(Principal principal, SupplyInfoUpdateCommand command);

    SupplyInfo update(Principal principal, String id, SupplyInfoUpdateCommand command);

    void delete(Principal principal, String id);
}
