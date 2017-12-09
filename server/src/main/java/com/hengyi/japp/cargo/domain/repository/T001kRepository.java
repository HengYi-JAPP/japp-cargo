package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.sap.T001k;

import java.util.stream.Stream;

public interface T001kRepository {
    T001k save(T001k t001k);

    T001k find(String id);

    Stream<T001k> queryAll();
}
