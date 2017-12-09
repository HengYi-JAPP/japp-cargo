package com.hengyi.japp.cargo.infrastructure.jms.mdb;

import com.hengyi.japp.cargo.application.ApplicationEvents;
import com.hengyi.japp.cargo.domain.meg.MegSendInfo;
import com.hengyi.japp.cargo.domain.repository.CarNoInfoLogRepository;
import com.hengyi.japp.cargo.domain.repository.MegSendInfoRepository;
import com.hengyi.japp.cargo.infrastructure.jms.JmsApplicationEvents;
import org.slf4j.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.inject.Inject;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Objects;

/**
 * Created by jzb on 17-4-15.
 */

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = JmsApplicationEvents.TOPIC),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class MegSendInfo_mdb implements MessageListener {
    @Resource
    private MessageDrivenContext mdc;
    @Inject
    private Logger log;
    @Inject
    private MegSendInfoRepository megSendInfoRepository;
    @Inject
    private CarNoInfoLogRepository carNoInfoLogRepository;

    @Override
    public void onMessage(Message inMessage) {
        try {
            MapMessage msg = (MapMessage) inMessage;
            String entityClass = msg.getString("entityClass");
            if (!Objects.equals(entityClass, MegSendInfo.class.getName())) {
                return;
            }

            final MegSendInfo sendInfo = megSendInfoRepository.find(msg.getString("id"));
            ApplicationEvents.EventType eventType = ApplicationEvents.EventType.valueOf(msg.getString("eventType"));
            switch (eventType) {
                case CREATE: {
                    carNoInfoLogRepository.save(sendInfo);
                    break;
                }
                case UPDATE: {
                    carNoInfoLogRepository.save(sendInfo);
                    break;
                }
                case DELETE: {
                    break;
                }
            }
        } catch (Throwable e) {
            log.error(inMessage + "", e);
        }
    }

}
