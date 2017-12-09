package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.config.TransCorp;
import com.hengyi.japp.cargo.domain.sap.T001;

import java.util.stream.Stream;

public interface TransCorpRepository {
    TransCorp find(String id);

    TransCorp save(TransCorp transCorp);

    Stream<TransCorp> queryAll();

    Stream<TransCorp> queryBy(T001 t001);
}
