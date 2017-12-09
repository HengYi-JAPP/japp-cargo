package com.hengyi.japp.cargo.interfaces.websocket.conf;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.security.Principal;

/**
 * 描述：
 *
 * @author jzb 2017-12-09
 */
public class MyConfigurator extends ServerEndpointConfig.Configurator {
    public static final String USERPROPERTY_HANDSHAKEREQUEST = "handshakeRequest";
    public static final String USERPROPERTY_PRINCIPAL = "principal";

    @Override
    public void modifyHandshake(ServerEndpointConfig conf, HandshakeRequest req, HandshakeResponse resp) {
        Principal principal = req.getUserPrincipal();
        conf.getUserProperties().put(USERPROPERTY_HANDSHAKEREQUEST, req);
        conf.getUserProperties().put(USERPROPERTY_PRINCIPAL, principal);
    }
}
