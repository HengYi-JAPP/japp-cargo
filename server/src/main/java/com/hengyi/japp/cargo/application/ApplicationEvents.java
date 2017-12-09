package com.hengyi.japp.cargo.application;

import java.security.Principal;

/**
 * 描述：
 *
 * @author jzb 2017-12-06
 */
public interface ApplicationEvents {

    void fireCreate(Class clazz, Principal principal, Object id, Object command) throws Exception;

    void fireUpdate(Class clazz, Principal principal, Object id, Object command) throws Exception;

    void fireDelete(Class clazz, Principal principal, Object id) throws Exception;

    enum EventType {
        CREATE,
        UPDATE,
        DELETE,
    }
}
