package com.hengyi.japp.cargo.application.internal;

import com.hengyi.japp.cargo.application.WharfT001lService;
import com.hengyi.japp.cargo.domain.config.WharfT001l;
import com.hengyi.japp.cargo.domain.repository.T001lRepository;
import com.hengyi.japp.cargo.domain.repository.WharfT001lRepository;
import com.hengyi.japp.cargo.domain.sap.T001l;
import com.hengyi.japp.cargo.domain.sap.T001lPK;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Principal;

@Stateless
public class WharfT001lServiceImpl implements WharfT001lService {
    @Inject
    private WharfT001lRepository wharfT001lRepository;
    @Inject
    private T001lRepository t001lRepository;

    @Override
    public WharfT001l add(Principal principal, T001lPK pk) {
        final T001l t001l = t001lRepository.find(pk);
        WharfT001l wharfT001l = new WharfT001l();
        wharfT001l.setT001l(t001l);
        return wharfT001lRepository.save(wharfT001l);
    }

    @Override
    public void remove(Principal principal, T001lPK pk) {
        final WharfT001l wharfT001l = wharfT001lRepository.find(pk);
        wharfT001l.setDeleted(true);
        wharfT001lRepository.save(wharfT001l);
    }
}
