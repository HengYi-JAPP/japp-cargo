package com.hengyi.japp.cargo.domain.config;

import com.hengyi.japp.cargo.domain.sap.T001;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SupplyInfo.class)
public abstract class SupplyInfo_ extends com.hengyi.japp.cargo.domain.AbstractLoggableEntity_ {

	public static volatile SingularAttribute<SupplyInfo, Boolean> deleted;
	public static volatile SingularAttribute<SupplyInfo, String> name;
	public static volatile SingularAttribute<SupplyInfo, String> id;
	public static volatile SetAttribute<SupplyInfo, T001> t001s;

}

