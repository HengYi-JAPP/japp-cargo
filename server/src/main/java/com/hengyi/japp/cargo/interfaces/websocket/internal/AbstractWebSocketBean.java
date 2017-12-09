package com.hengyi.japp.cargo.interfaces.websocket.internal;

import com.google.common.collect.Queues;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.security.Principal;
import java.util.Queue;

import static com.hengyi.japp.cargo.interfaces.websocket.conf.MyConfigurator.USERPROPERTY_PRINCIPAL;

/**
 * 描述：
 *
 * @author jzb 2017-12-09
 */
public abstract class AbstractWebSocketBean {
    protected final Queue<Session> queue = Queues.newConcurrentLinkedQueue();
    @Inject
    protected Logger log;

    public static void setSession(Session session, String key, Object value) {
        session.getUserProperties().put(key, value);
    }

    public static <T> T getSession(Session session, String key) {
        return (T) session.getUserProperties().get(key);
    }

    public void onOpen(Session session, EndpointConfig conf) {
        final Principal principal = (Principal) conf.getUserProperties().get(USERPROPERTY_PRINCIPAL);
        session.getUserProperties().put(USERPROPERTY_PRINCIPAL, principal);
        queue.add(session);
    }

    public void onClose(Session session) {
        queue.remove(session);
    }

    public void onError(Session session, Throwable t) {
        log.error("", t);
        onClose(session);
    }
}
