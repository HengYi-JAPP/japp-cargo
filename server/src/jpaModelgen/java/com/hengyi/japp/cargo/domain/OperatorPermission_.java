package com.hengyi.japp.cargo.domain;

import com.hengyi.japp.cargo.domain.sap.T001;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OperatorPermission.class)
public abstract class OperatorPermission_ {

	public static volatile SingularAttribute<OperatorPermission, T001> defaultReceiveT001;
	public static volatile SingularAttribute<OperatorPermission, Boolean> allT001s;
	public static volatile SingularAttribute<OperatorPermission, String> id;
	public static volatile SingularAttribute<OperatorPermission, Operator> operator;
	public static volatile SetAttribute<OperatorPermission, T001> t001s;

}

