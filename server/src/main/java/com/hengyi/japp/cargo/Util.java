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

    public static String packTypeString(String packType) {
        switch (packType) {
            case "1":
                return "槽车";
            case "2":
                return "1.1吨新袋";
            case "5":
                return "1.2吨新袋";
            case "3":
                return "1.1吨旧袋";
            default:
                return "非正常";
        }
    }

    public static String transTypeString(String transType) {
        switch (transType) {
            case "1":
                return "槽车";
            case "2":
                return "平板车";
            case "3":
                return "集装箱";
            case "-1":
                return "船运";
            default:
                return "非正常";
        }
    }
}
