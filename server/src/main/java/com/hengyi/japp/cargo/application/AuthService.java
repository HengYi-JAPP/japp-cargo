package com.hengyi.japp.cargo.application;

import com.hengyi.japp.cargo.domain.sap.T001;

import java.security.Principal;

/**
 * 描述：
 *
 * @author jzb 2017-12-02
 */
public interface AuthService {
    T001 defaultT001(Principal principal);
}
