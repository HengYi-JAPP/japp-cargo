package com.hengyi.japp.cargo.interfaces.websocket;

import javax.websocket.EndpointConfig;
import javax.websocket.Session;

/**
 * 描述：
 *
 * @author jzb 2017-12-09
 */
public interface WebSocketGpsBean {
    void onOpen(Session session, EndpointConfig conf, String carNo);

    void onClose(Session session);

    void onError(Session session, Throwable t);
}
