package com.hengyi.japp.cargo.domain.pta;

import com.hengyi.japp.cargo.domain.sap.Ekpo;
import com.hengyi.japp.cargo.domain.sap.T001l;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PtaSapReceiveInfo.class)
public abstract class PtaSapReceiveInfo_ {

	public static volatile SingularAttribute<PtaSapReceiveInfo, String> sapNo;
	public static volatile SingularAttribute<PtaSapReceiveInfo, BigDecimal> amount;
	public static volatile SingularAttribute<PtaSapReceiveInfo, T001l> t001l;
	public static volatile SingularAttribute<PtaSapReceiveInfo, PtaReceiveInfo> receiveInfo;
	public static volatile SingularAttribute<PtaSapReceiveInfo, Ekpo> ekpo;
	public static volatile SingularAttribute<PtaSapReceiveInfo, String> id;

}

