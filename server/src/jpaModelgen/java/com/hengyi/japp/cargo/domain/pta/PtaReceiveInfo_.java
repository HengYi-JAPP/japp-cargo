package com.hengyi.japp.cargo.domain.pta;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PtaReceiveInfo.class)
public abstract class PtaReceiveInfo_ extends com.hengyi.japp.cargo.domain.AbstractReceiveInfoEntity_ {

    public static volatile SingularAttribute<PtaReceiveInfo, String> poundNo;
    public static volatile SetAttribute<PtaReceiveInfo, PtaSapReceiveInfo> sapReceiveInfos;
    public static volatile SingularAttribute<PtaReceiveInfo, BigDecimal> littleLfimgPerPack;

}

