package com.hengyi.japp.cargo.domain.meg;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MegReceiveInfo.class)
public abstract class MegReceiveInfo_ extends com.hengyi.japp.cargo.domain.AbstractReceiveInfoEntity_ {

    public static volatile SetAttribute<MegReceiveInfo, MegSapReceiveInfo> sapReceiveInfos;
    public static volatile SingularAttribute<MegReceiveInfo, String> pickPoundNo;

}

