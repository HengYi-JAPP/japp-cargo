package com.hengyi.japp.cargo.domain;

import com.hengyi.japp.cargo.domain.config.TransCorp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CarNoInfoLog.class)
public abstract class CarNoInfoLog_ {

	public static volatile SingularAttribute<CarNoInfoLog, String> carNo;
	public static volatile SingularAttribute<CarNoInfoLog, String> carDriver;
	public static volatile SingularAttribute<CarNoInfoLog, TransCorp> transCorp;

}

