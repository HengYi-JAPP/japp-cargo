package com.hengyi.japp.cargo.domain.config;

import com.hengyi.japp.cargo.domain.sap.T001;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TransCorp.class)
public abstract class TransCorp_ extends com.hengyi.japp.cargo.domain.AbstractLoggableEntity_ {

    public static volatile SingularAttribute<TransCorp, Boolean> deleted;
    public static volatile SingularAttribute<TransCorp, String> name;
    public static volatile SingularAttribute<TransCorp, String> id;
    public static volatile SetAttribute<TransCorp, T001> t001s;

}

