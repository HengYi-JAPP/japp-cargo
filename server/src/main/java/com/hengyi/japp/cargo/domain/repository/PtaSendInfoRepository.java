package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.application.query.PtaSendInfoQuery;
import com.hengyi.japp.cargo.domain.pta.PtaSendInfo;

public interface PtaSendInfoRepository {
    PtaSendInfo find(String id);

    PtaSendInfo save(PtaSendInfo sendInfo);

    void query(PtaSendInfoQuery query);
}
