package com.hengyi.japp.cargo.infrastructure.jms;

import com.hengyi.japp.cargo.application.ApplicationEvents;
import org.jzb.J;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.*;
import java.security.Principal;

/**
 * 描述：
 *
 * @author jzb 2017-12-06
 */
@ApplicationScoped
public class JmsApplicationEvents implements ApplicationEvents {
    public static final String QUEUE = "java:/jms/japp-cargo-Queue";
    public static final String TOPIC = "java:/jms/japp-cargo-Topic";
    @Inject
    private JMSContext jmsContext;
    @Resource(mappedName = QUEUE)
    private Queue queue;
    @Resource(mappedName = TOPIC)
    private Topic topic;

    @Override
    public void fireCreate(Class clazz, Principal principal, Object id, Object command) throws JMSException {
        fire(EventType.CREATE, clazz, principal, id, command);
    }

    private void fire(EventType eventType, Class clazz, Principal principal, Object id, Object command) throws JMSException {
        MapMessage message = jmsContext.createMapMessage();
        message.setString("principal", principal.getName());
        message.setString("entityClass", clazz.getName());
        message.setObject("id", id);
        message.setString("eventType", eventType.name());
        message.setString("command", J.toJson(command));
        jmsContext.createProducer().send(topic, message);
    }

    @Override
    public void fireUpdate(Class clazz, Principal principal, Object id, Object command) throws JMSException {
        fire(EventType.UPDATE, clazz, principal, id, command);
    }

    @Override
    public void fireDelete(Class clazz, Principal principal, Object id) throws Exception {
        MapMessage message = jmsContext.createMapMessage();
        message.setString("principal", principal.getName());
        message.setString("entityClass", clazz.getName());
        message.setObject("id", id);
        message.setString("eventType", EventType.DELETE.name());
        jmsContext.createProducer().send(topic, message);
    }
}
