package com.hengyi.japp.cargo.domain.repository;

import com.hengyi.japp.cargo.application.query.MegSendInfoQuery;
import com.hengyi.japp.cargo.domain.meg.MegSendInfo;

public interface MegSendInfoRepository {
    MegSendInfo find(String id);

    MegSendInfo save(MegSendInfo sendInfo);

    void query(MegSendInfoQuery megSendInfoQuery);
}
