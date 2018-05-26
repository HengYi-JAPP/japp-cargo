package com.hengyi.japp.cargo.domain.pta;

import com.hengyi.japp.cargo.domain.sap.Ylips;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PtaSendInfo.class)
public abstract class PtaSendInfo_ extends com.hengyi.japp.cargo.domain.AbstractSendInfoEntity_ {

	public static volatile SingularAttribute<PtaSendInfo, String> batchNo;
	public static volatile SingularAttribute<PtaSendInfo, Ylips> ylips;
	public static volatile SingularAttribute<PtaSendInfo, String> transType;
	public static volatile SingularAttribute<PtaSendInfo, String> packType;
	public static volatile SingularAttribute<PtaSendInfo, PtaReceiveInfo> receiveInfo;
	public static volatile SingularAttribute<PtaSendInfo, Integer> packNo;

}

