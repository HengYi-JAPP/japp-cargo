package com.hengyi.japp.cargo.domain.config;

import com.hengyi.japp.cargo.domain.sap.T001;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(HeadInfo.class)
public abstract class HeadInfo_ extends com.hengyi.japp.cargo.domain.AbstractLoggableEntity_ {

	public static volatile SingularAttribute<HeadInfo, Boolean> deleted;
	public static volatile SingularAttribute<HeadInfo, String> name;
	public static volatile SingularAttribute<HeadInfo, String> id;
	public static volatile SetAttribute<HeadInfo, T001> t001s;

}

