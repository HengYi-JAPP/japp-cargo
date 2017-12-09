package com.hengyi.japp.cargo.interfaces.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.hengyi.japp.cargo.interfaces.websocket.conf.JsonDecoder;
import com.hengyi.japp.cargo.interfaces.websocket.conf.JsonEncoder;
import com.hengyi.japp.cargo.interfaces.websocket.conf.MyConfigurator;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by jzb on 16-3-28.
 */
@ServerEndpoint(value = "/global", configurator = MyConfigurator.class, encoders = {JsonEncoder.class}, decoders = {JsonDecoder.class})
public class GlobalEndpoint {
    @Inject
    private WebSocketGlobalBean bean;

    @OnOpen
    public void onOpen(Session session, EndpointConfig conf) {
        bean.onOpen(session, conf);
    }

    @OnMessage
    public void onMessage(Session session, JsonNode node) {
    }

    @OnClose
    public void onClose(Session session) {
        bean.onClose(session);
    }

    @OnError
    public void onError(Session session, Throwable t) {
        bean.onError(session, t);
    }
}
