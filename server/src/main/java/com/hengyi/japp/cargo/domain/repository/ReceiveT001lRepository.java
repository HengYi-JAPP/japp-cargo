package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.config.ReceiveT001l;
import com.hengyi.japp.cargo.domain.sap.T001;
import com.hengyi.japp.cargo.domain.sap.T001lPK;

import java.util.stream.Stream;

public interface ReceiveT001lRepository {
    ReceiveT001l save(ReceiveT001l t001);

    ReceiveT001l find(T001lPK pk);

    Stream<ReceiveT001l> queryAll();

    Stream<ReceiveT001l> queryBy(T001 t001);
}
