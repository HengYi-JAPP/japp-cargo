package com.hengyi.japp.cargo;

import com.hengyi.japp.sap.client.JSap;
import com.hengyi.japp.sap.client.RfcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 描述：
 *
 * @author jzb 2017-11-20
 */
public class Resource {
    @Produces
    @PersistenceContext
    private EntityManager em;

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }

    @Produces
    public RfcClient produceLog() {
        return JSap.grpcClient();
    }
}
