package com.hengyi.japp.cargo.domain;

import com.hengyi.japp.cargo.domain.sap.T001;
import com.hengyi.japp.cargo.domain.sap.T001l;
import com.hengyi.japp.cargo.domain.sap.T001w;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AbstractReceiveInfoEntity.class)
public abstract class AbstractReceiveInfoEntity_ {

	public static volatile SingularAttribute<AbstractReceiveInfoEntity, String> note;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, T001w> t001w;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, T001> t001;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, BigDecimal> lfimg;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, Date> receiveDate;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, BigDecimal> lfimg1;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, BigDecimal> lfimg2;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, T001l> t001l;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, String> meins;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, BigDecimal> diffLfimg1;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, BigDecimal> diffLfimg2;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, String> id;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, String> meins1;
	public static volatile SingularAttribute<AbstractReceiveInfoEntity, String> meins2;

}

