package com.hengyi.japp.cargo.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AbstractLoggableEntity.class)
public abstract class AbstractLoggableEntity_ {

	public static volatile SingularAttribute<AbstractLoggableEntity, Operator> creator;
	public static volatile SingularAttribute<AbstractLoggableEntity, Date> modifyDateTime;
	public static volatile SingularAttribute<AbstractLoggableEntity, Operator> modifier;
	public static volatile SingularAttribute<AbstractLoggableEntity, Date> createDateTime;

}

