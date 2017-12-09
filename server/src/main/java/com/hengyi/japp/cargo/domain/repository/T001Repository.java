package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.sap.T001;
import com.hengyi.japp.cargo.domain.sap.T001w;

import java.util.stream.Stream;

public interface T001Repository {
    T001 save(T001 t001);

    T001 find(T001w t001w);

    T001 find(String id);

    Stream<T001> queryAll();
}
