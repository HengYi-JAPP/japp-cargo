package com.hengyi.japp.cargo.application.internal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.hengyi.japp.cargo.domain.repository.*;
import com.hengyi.japp.cargo.domain.sap.*;
import com.hengyi.japp.sap.RfcExeCommand;
import com.hengyi.japp.sap.client.RfcClient;
import com.hengyi.japp.sap.grpc.server.ExeRfcReply;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static org.jzb.Constant.MAPPER;

/**
 * 描述： 每天12点，同步基础数据一次
 *
 * @author jzb 2017-12-01
 */
@Startup
@Singleton
@AccessTimeout(value = 4, unit = TimeUnit.HOURS)
public class SapSyncSchedule {
    @Inject
    private RfcClient rfcClient;
    @Inject
    private Lfa1Repository lfa1Repository;
    @Inject
    private Lfb1Repository lfb1Repository;
    @Inject
    private T001Repository t001Repository;
    @Inject
    private Kna1Repository kna1Repository;
    @Inject
    private Knb1Repository knb1Repository;
    @Inject
    private T001lRepository t001lRepository;
    @Inject
    private T001wRepository t001wRepository;
    @Inject
    private T001kRepository t001kRepository;

    @Schedule
    public void sync() throws Exception {
        RfcExeCommand command = new RfcExeCommand();
        final ExeRfcReply res = rfcClient.exeRfc("ZJAPP_CARGO_1", command);
        final JsonNode tables = MAPPER.readTree(res.getTables());

        final JsonNode ET_LFA1 = tables.get("ET_LFA1");
        CollectionLikeType likeType = MAPPER.getTypeFactory().constructCollectionLikeType(ArrayList.class, Lfa1.class);
        final Collection<Lfa1> lfa1s = MAPPER.convertValue(ET_LFA1, likeType);
        lfa1s.stream().forEach(lfa1Repository::save);

        final JsonNode ET_LFB1 = tables.get("ET_LFB1");
        likeType = MAPPER.getTypeFactory().constructCollectionLikeType(ArrayList.class, Lfb1.class);
        final Collection<Lfb1> lfb1s = MAPPER.convertValue(ET_LFB1, likeType);
        lfb1s.stream().forEach(lfb1Repository::save);

        final JsonNode ET_T001 = tables.get("ET_T001");
        likeType = MAPPER.getTypeFactory().constructCollectionLikeType(ArrayList.class, T001.class);
        final Collection<T001> t001s = MAPPER.convertValue(ET_T001, likeType);
        t001s.stream().forEach(t001Repository::save);

        final JsonNode ET_KNA1 = tables.get("ET_KNA1");
        likeType = MAPPER.getTypeFactory().constructCollectionLikeType(ArrayList.class, Kna1.class);
        final Collection<Kna1> kna1s = MAPPER.convertValue(ET_KNA1, likeType);
        kna1s.stream().forEach(kna1Repository::save);

        final JsonNode ET_KNB1 = tables.get("ET_KNB1");
        likeType = MAPPER.getTypeFactory().constructCollectionLikeType(ArrayList.class, Knb1.class);
        final Collection<Knb1> knb1s = MAPPER.convertValue(ET_KNB1, likeType);
        knb1s.stream().forEach(knb1Repository::save);

        final JsonNode ET_T001L = tables.get("ET_T001L");
        likeType = MAPPER.getTypeFactory().constructCollectionLikeType(ArrayList.class, T001l.class);
        final Collection<T001l> t001ls = MAPPER.convertValue(ET_T001L, likeType);
        t001ls.stream().forEach(t001lRepository::save);

        final JsonNode ET_T001W = tables.get("ET_T001W");
        likeType = MAPPER.getTypeFactory().constructCollectionLikeType(ArrayList.class, T001w.class);
        final Collection<T001w> t001ws = MAPPER.convertValue(ET_T001W, likeType);
        t001ws.stream().forEach(t001wRepository::save);

        final JsonNode ET_T001K = tables.get("ET_T001K");
        likeType = MAPPER.getTypeFactory().constructCollectionLikeType(ArrayList.class, T001k.class);
        final Collection<T001k> t001ks = MAPPER.convertValue(ET_T001K, likeType);
        t001ks.stream().forEach(t001kRepository::save);
    }
}
