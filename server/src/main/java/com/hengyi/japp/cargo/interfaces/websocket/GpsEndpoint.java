package com.hengyi.japp.cargo.interfaces.websocket;

import com.hengyi.japp.cargo.interfaces.websocket.conf.JsonEncoder;
import org.hibernate.validator.constraints.NotBlank;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Created by jzb on 16-3-28.
 */
@ServerEndpoint(value = "/gps/{carNo}", encoders = {JsonEncoder.class})
public class GpsEndpoint {
    @Inject
    private WebSocketGpsBean bean;

    @OnOpen
    public void onOpen(Session session, EndpointConfig conf, @Valid @NotBlank @PathParam("carNo") String carNo) {
        bean.onOpen(session, conf, carNo);
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
