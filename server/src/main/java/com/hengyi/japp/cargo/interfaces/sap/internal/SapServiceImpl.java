package com.hengyi.japp.cargo.interfaces.sap.internal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.github.ixtf.japp.core.J;
import com.hengyi.japp.cargo.domain.sap.Likp;
import com.hengyi.japp.cargo.domain.sap.Lips;
import com.hengyi.japp.cargo.domain.sap.LipsPK;
import com.hengyi.japp.cargo.domain.sap.Ylips;
import com.hengyi.japp.cargo.interfaces.sap.SapService;
import com.hengyi.japp.sap.RfcExeCommand;
import com.hengyi.japp.sap.client.RfcClient;
import com.hengyi.japp.sap.grpc.server.ExeRfcReply;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.github.ixtf.japp.core.Constant.MAPPER;

/**
 * 描述：
 *
 * @author jzb 2017-12-01
 */
@Stateless
public class SapServiceImpl implements SapService {
    private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
    @Inject
    private RfcClient rfcClient;

    @Override
    public Ylips findYlips(String ylipsId) throws Exception {
        RfcExeCommand command = new RfcExeCommand();
        final ObjectNode imports = MAPPER.createObjectNode().put("I_POUND_NO", ylipsId);
        command.setImports(imports);
        final ExeRfcReply res = rfcClient.exeRfc("ZJAPP_CARGO_3", command);
        final JsonNode exports = MAPPER.readTree(res.getExports());
        final Likp likp = MAPPER.convertValue(exports.get("E_LIKP"), Likp.class);
        Lips lips = MAPPER.convertValue(exports.get("E_LIPS"), Lips.class);
        lips.setLikp(likp);
        Ylips ylips = MAPPER.convertValue(exports.get("E_YLIPS"), Ylips.class);
        ylips.setLips(lips);
        return ylips;
    }

    @Override
    public Stream<Ylips> queryYlips(LocalDate ld) throws Exception {
        RfcExeCommand command = new RfcExeCommand();
        final ObjectNode imports = MAPPER.createObjectNode().put("I_DATE", ld.format(dtf));
        command.setImports(imports);
        final ExeRfcReply res = rfcClient.exeRfc("ZJAPP_CARGO_2", command);
        final JsonNode tables = MAPPER.readTree(res.getTables());

        final JsonNode ET_LIKP = tables.get("ET_LIKP");
        CollectionLikeType likeType = MAPPER.getTypeFactory().constructCollectionLikeType(ArrayList.class, Likp.class);
        final Collection<Likp> likps = MAPPER.convertValue(ET_LIKP, likeType);
        final Map<String, Likp> likpMap = likps.stream().collect(Collectors.toMap(Likp::getVbeln, Function.identity()));

        final JsonNode ET_LIPS = tables.get("ET_LIPS");
        likeType = MAPPER.getTypeFactory().constructCollectionLikeType(ArrayList.class, Lips.class);
        final Collection<Lips> lipses = MAPPER.convertValue(ET_LIPS, likeType);
        final Map<LipsPK, Lips> lipsMap = lipses.stream().collect(Collectors.toMap(Lips::pk, Function.identity()));

        final ArrayNode arrayNode = (ArrayNode) tables.get("ET_YLIPS");
        return StreamSupport.stream(arrayNode.spliterator(), false)
                .map(it -> {
                    LipsPK pk = new LipsPK();
                    pk.setVbeln(it.get("VBELN").asText());
                    pk.setPosnr(it.get("POSNR").asText());
                    /**
                     * 工作联络单问题，
                     * SAP交货单会被冲销，但由于发货次数过多，地磅单不冲销了
                     */
                    return Optional.ofNullable(pk.getVbeln())
                            .filter(J::nonBlank)
                            .map(likpMap::get)
                            .flatMap(likp -> Optional.ofNullable(lipsMap.get(pk))
                                    .map(lips -> {
                                        lips.setLikp(likp);
                                        return lips;
                                    })
                            )
                            .map(lips -> {
                                Ylips ylips = MAPPER.convertValue(it, Ylips.class);
                                ylips.setLips(lips);
                                return ylips;
                            })
                            .orElse(null);
                })
                .filter(Objects::nonNull);
    }

}
