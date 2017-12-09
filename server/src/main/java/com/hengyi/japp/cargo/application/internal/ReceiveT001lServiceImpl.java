package com.hengyi.japp.cargo.application.internal;

import com.hengyi.japp.cargo.application.ReceiveT001lService;
import com.hengyi.japp.cargo.domain.config.ReceiveT001l;
import com.hengyi.japp.cargo.domain.repository.ReceiveT001lRepository;
import com.hengyi.japp.cargo.domain.repository.T001lRepository;
import com.hengyi.japp.cargo.domain.sap.T001l;
import com.hengyi.japp.cargo.domain.sap.T001lPK;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.Principal;

@Stateless
public class ReceiveT001lServiceImpl implements ReceiveT001lService {
    @Inject
    private ReceiveT001lRepository receiveT001lRepository;
    @Inject
    private T001lRepository t001lRepository;

    @Override
    public ReceiveT001l add(Principal principal, T001lPK pk) {
        final T001l t001l = t001lRepository.find(pk);
        ReceiveT001l receiveT001l = new ReceiveT001l();
        receiveT001l.setT001l(t001l);
        return receiveT001lRepository.save(receiveT001l);
    }

    @Override
    public void remove(Principal principal, T001lPK pk) {
        final ReceiveT001l receiveT001l = receiveT001lRepository.find(pk);
        receiveT001l.setDeleted(true);
        receiveT001lRepository.save(receiveT001l);
    }
}
