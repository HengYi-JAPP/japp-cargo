package com.hengyi.japp.cargo.domain;

import com.hengyi.japp.cargo.domain.config.TransCorp;
import com.hengyi.japp.cargo.domain.sap.Lfa1;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;
import java.util.Date;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AbstractSendInfoEntity.class)
public abstract class AbstractSendInfoEntity_ extends com.hengyi.japp.cargo.domain.AbstractLoggableEntity_ {

    public static volatile SingularAttribute<AbstractSendInfoEntity, String> note;
    public static volatile SingularAttribute<AbstractSendInfoEntity, Date> sendDate;
    public static volatile SingularAttribute<AbstractSendInfoEntity, BigDecimal> lfimg;
    public static volatile SingularAttribute<AbstractSendInfoEntity, String> carDriver;
    public static volatile SingularAttribute<AbstractSendInfoEntity, Long> version;
    public static volatile SingularAttribute<AbstractSendInfoEntity, BigDecimal> lfimg1;
    public static volatile SingularAttribute<AbstractSendInfoEntity, Boolean> deleted;
    public static volatile SingularAttribute<AbstractSendInfoEntity, BigDecimal> lfimg2;
    public static volatile SingularAttribute<AbstractSendInfoEntity, String> carNo;
    public static volatile SingularAttribute<AbstractSendInfoEntity, Lfa1> lfa1;
    public static volatile SingularAttribute<AbstractSendInfoEntity, String> meins;
    public static volatile SingularAttribute<AbstractSendInfoEntity, String> id;
    public static volatile SingularAttribute<AbstractSendInfoEntity, String> meins1;
    public static volatile SingularAttribute<AbstractSendInfoEntity, TransCorp> transCorp;
    public static volatile SingularAttribute<AbstractSendInfoEntity, String> meins2;

}

