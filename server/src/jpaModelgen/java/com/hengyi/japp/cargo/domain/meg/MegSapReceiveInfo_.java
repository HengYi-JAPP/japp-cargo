package com.hengyi.japp.cargo.domain.meg;

import com.hengyi.japp.cargo.domain.sap.Ekpo;
import com.hengyi.japp.cargo.domain.sap.T001l;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MegSapReceiveInfo.class)
public abstract class MegSapReceiveInfo_ {

    public static volatile SingularAttribute<MegSapReceiveInfo, String> sapNo;
    public static volatile SingularAttribute<MegSapReceiveInfo, BigDecimal> amount;
    public static volatile SingularAttribute<MegSapReceiveInfo, T001l> t001l;
    public static volatile SingularAttribute<MegSapReceiveInfo, MegReceiveInfo> receiveInfo;
    public static volatile SingularAttribute<MegSapReceiveInfo, Ekpo> ekpo;
    public static volatile SingularAttribute<MegSapReceiveInfo, String> id;

}

