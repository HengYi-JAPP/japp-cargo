package com.hengyi.japp.cargo.domain.meg;

import com.hengyi.japp.cargo.domain.sap.T001l;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MegSendInfo.class)
public abstract class MegSendInfo_ extends com.hengyi.japp.cargo.domain.AbstractSendInfoEntity_ {

	public static volatile SingularAttribute<MegSendInfo, T001l> wharf;
	public static volatile SingularAttribute<MegSendInfo, MegReceiveInfo> receiveInfo;
	public static volatile SingularAttribute<MegSendInfo, Integer> megType;

}

