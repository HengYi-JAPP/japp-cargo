package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.config.SupplyInfo;
import com.hengyi.japp.cargo.domain.sap.T001;

import java.util.stream.Stream;

public interface SupplyInfoRepository {
    SupplyInfo find(String id);

    SupplyInfo save(SupplyInfo supplyInfo);

    Stream<SupplyInfo> queryAll();

    Stream<SupplyInfo> queryBy(T001 t001);
}
