package com.hengyi.japp.cargo.application;

import com.hengyi.japp.cargo.application.command.ReceiveT001AddCommand;
import com.hengyi.japp.cargo.domain.config.ReceiveT001;

import java.security.Principal;

public interface ReceiveT001Service {
    ReceiveT001 add(Principal principal, ReceiveT001AddCommand command);

    void remove(Principal principal, String id);
}
