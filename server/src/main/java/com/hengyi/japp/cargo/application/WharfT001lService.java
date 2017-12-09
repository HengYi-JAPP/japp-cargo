package com.hengyi.japp.cargo.application;

import com.hengyi.japp.cargo.domain.config.WharfT001l;
import com.hengyi.japp.cargo.domain.sap.T001lPK;

import java.security.Principal;

public interface WharfT001lService {

    WharfT001l add(Principal principal, T001lPK pk);

    void remove(Principal principal, T001lPK pk);
}
