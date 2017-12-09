package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.config.ReceiveT001;

import java.util.stream.Stream;

public interface ReceiveT001Repository {
    ReceiveT001 save(ReceiveT001 t001);

    ReceiveT001 find(String id);

    Stream<ReceiveT001> queryAll();
}
