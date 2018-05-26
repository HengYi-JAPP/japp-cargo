package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.application.query.PtaSendInfoQuery;
import com.hengyi.japp.cargo.domain.pta.PtaSendInfo;
import com.hengyi.japp.cargo.domain.sap.Ylips;

import java.security.Principal;
import java.time.LocalDate;
import java.util.stream.Stream;

public interface PtaSendInfoRepository {
    PtaSendInfo find(String id);

    PtaSendInfo save(PtaSendInfo sendInfo);

    PtaSendInfo queryBy(Ylips ylips);

    Stream<PtaSendInfo> querySendInfo(Principal principal, LocalDate ld);

    void query(PtaSendInfoQuery query);
}
