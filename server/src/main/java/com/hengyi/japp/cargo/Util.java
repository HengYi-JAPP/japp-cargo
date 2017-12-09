package com.hengyi.japp.cargo;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Created by jzb on 16-10-26.
 */
public class Util {
    public static <T> T getSingle(TypedQuery<T> q) {
        try {
            return q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
