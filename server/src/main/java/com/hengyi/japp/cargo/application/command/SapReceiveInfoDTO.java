package com.hengyi.japp.cargo.application.command;

import com.hengyi.japp.cargo.domain.sap.EkpoPK;
import com.hengyi.japp.cargo.domain.sap.T001lPK;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
public class SapReceiveInfoDTO implements Serializable {
    private String id;
    @NotNull
    private String sapNo;
    private BigDecimal amount;
    private T001lPK t001l;
    private EkpoPK ekpo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSapNo() {
        return sapNo;
    }

    public void setSapNo(String sapNo) {
        this.sapNo = sapNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public T001lPK getT001l() {
        return t001l;
    }

    public void setT001l(T001lPK t001l) {
        this.t001l = t001l;
    }

    public EkpoPK getEkpo() {
        return ekpo;
    }

    public void setEkpo(EkpoPK ekpo) {
        this.ekpo = ekpo;
    }
}
