package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.application.query.MegSendInfoQuery;
import com.hengyi.japp.cargo.domain.meg.MegSendInfo;

import java.security.Principal;
import java.time.LocalDate;
import java.util.stream.Stream;

public interface MegSendInfoRepository {
    MegSendInfo find(String id);

    MegSendInfo save(MegSendInfo sendInfo);

    /**
     * 收货员当天所有的收货明细
     *
     * @param principal
     * @param ld
     * @return
     */
    Stream<MegSendInfo> querySendInfo(Principal principal, LocalDate ld);

    void query(MegSendInfoQuery megSendInfoQuery);
}
