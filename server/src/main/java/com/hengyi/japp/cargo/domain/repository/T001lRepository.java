package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.sap.T001l;
import com.hengyi.japp.cargo.domain.sap.T001lPK;

import java.util.stream.Stream;

public interface T001lRepository {
    T001l save(T001l t001l);

    T001l find(T001lPK pk);

    Stream<T001l> queryAll();
}
