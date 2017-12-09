package com.hengyi.japp.cargo.application;

import com.hengyi.japp.cargo.application.command.TransCorpUpdateCommand;
import com.hengyi.japp.cargo.domain.config.TransCorp;

import java.security.Principal;

public interface TransCorpService {
    TransCorp create(Principal principal, TransCorpUpdateCommand command);

    TransCorp update(Principal principal, String id, TransCorpUpdateCommand command);

    void delete(Principal principal, String id);
}
