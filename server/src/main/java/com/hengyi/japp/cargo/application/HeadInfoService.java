package com.hengyi.japp.cargo.application;

import com.hengyi.japp.cargo.application.command.HeadInfoUpdateCommand;
import com.hengyi.japp.cargo.domain.config.HeadInfo;

import java.security.Principal;

public interface HeadInfoService {
    HeadInfo create(Principal principal, HeadInfoUpdateCommand command);

    HeadInfo update(Principal principal, String id, HeadInfoUpdateCommand command);

    void delete(Principal principal, String id);
}
