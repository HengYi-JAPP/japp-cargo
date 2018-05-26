package com.hengyi.japp.cargo.domain.config;

import com.hengyi.japp.cargo.domain.sap.Kna1;
import com.hengyi.japp.cargo.domain.sap.Lfa1;
import com.hengyi.japp.cargo.domain.sap.T001;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ReceiveT001.class)
public abstract class ReceiveT001_ extends com.hengyi.japp.cargo.domain.AbstractLoggableEntity_ {

	public static volatile SingularAttribute<ReceiveT001, Kna1> kna1;
	public static volatile SingularAttribute<ReceiveT001, String> bukrs;
	public static volatile SingularAttribute<ReceiveT001, Boolean> deleted;
	public static volatile SingularAttribute<ReceiveT001, T001> t001;
	public static volatile SingularAttribute<ReceiveT001, Lfa1> lfa1;

}

