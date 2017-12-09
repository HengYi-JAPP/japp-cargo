package com.hengyi.japp.cargo.domain.pta;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hengyi.japp.cargo.domain.sap.Ekpo;
import com.hengyi.japp.cargo.domain.sap.T001l;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
@Entity
@Table(name = "T_PTASAPRECEIVEINFO")
public class PtaSapReceiveInfo implements Serializable {
    @Id
    private String id;
    @JsonIgnore
    @ManyToOne
    private PtaReceiveInfo receiveInfo;
    /**
     * sap 收货订单号
     */
    private String sapNo;
    @Column(scale = 2)
    private BigDecimal amount;
    @ManyToOne
    private T001l t001l;
    @ManyToOne
    private Ekpo ekpo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PtaReceiveInfo getReceiveInfo() {
        return receiveInfo;
    }

    public void setReceiveInfo(PtaReceiveInfo receiveInfo) {
        this.receiveInfo = receiveInfo;
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

    public T001l getT001l() {
        return t001l;
    }

    public void setT001l(T001l t001l) {
        this.t001l = t001l;
    }

    public Ekpo getEkpo() {
        return ekpo;
    }

    public void setEkpo(Ekpo ekpo) {
        this.ekpo = ekpo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PtaSapReceiveInfo that = (PtaSapReceiveInfo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
