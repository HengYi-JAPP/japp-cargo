package com.hengyi.japp.cargo.application;

import com.hengyi.japp.cargo.application.command.OperatorPermissionUpdateCommand;
import com.hengyi.japp.cargo.domain.Operator;
import com.hengyi.japp.cargo.domain.OperatorPermission;

import java.security.Principal;

public interface OperatorService {

    Operator findByCas();

    OperatorPermission update(Principal principal, String id, OperatorPermissionUpdateCommand command);
}
