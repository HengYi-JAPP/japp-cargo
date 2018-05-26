package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.domain.config.HeadInfo;
import com.hengyi.japp.cargo.domain.sap.T001;

import java.util.stream.Stream;

public interface HeadInfoRepository {
    HeadInfo find(String id);

    HeadInfo save(HeadInfo headInfo);

    Stream<HeadInfo> queryAll();

    Stream<HeadInfo> queryBy(T001 t001);
}
