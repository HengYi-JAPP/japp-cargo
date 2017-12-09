package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.config.WharfT001l;
import com.hengyi.japp.cargo.domain.sap.T001;
import com.hengyi.japp.cargo.domain.sap.T001lPK;

import java.util.stream.Stream;

public interface WharfT001lRepository {

    WharfT001l save(WharfT001l wharfT001l);

    WharfT001l find(T001lPK pk);

    Stream<WharfT001l> queryAll();

    Stream<WharfT001l> queryBy(T001 t001);
}
