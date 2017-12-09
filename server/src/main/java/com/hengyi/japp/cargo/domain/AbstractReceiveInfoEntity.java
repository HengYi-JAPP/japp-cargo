package com.hengyi.japp.cargo.domain;

import com.hengyi.japp.cargo.domain.sap.T001;
import com.hengyi.japp.cargo.domain.sap.T001l;
import com.hengyi.japp.cargo.domain.sap.T001w;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 描述：
 *
 * @author jzb 2017-12-07
 */
@MappedSuperclass
public abstract class AbstractReceiveInfoEntity implements Serializable {
    @Id
    private String id;
    @ManyToOne
    private T001 t001;
    @NotNull
    @ManyToOne
    private T001w t001w;
    @NotNull
    @ManyToOne
    private T001l t001l;
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date receiveDate;
    /**
     * 皮重
     */
    @Column(scale = 2)
    private BigDecimal lfimg1;
    private String meins1 = "KG";
    /**
     * 毛重
     */
    @Column(scale = 2)
    private BigDecimal lfimg2;
    private String meins2 = "KG";
    /**
     * 净重
     */
    @Column(scale = 2)
    private BigDecimal lfimg;
    private String meins = "KG";
    /**
     * 地磅调整数
     */
    @Column(scale = 2)
    private BigDecimal diffLfimg1;
    /**
     * 特殊调整数
     */
    @Column(scale = 2)
    private BigDecimal diffLfimg2;
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T001 getT001() {
        return t001;
    }

    public void setT001(T001 t001) {
        this.t001 = t001;
    }

    public T001w getT001w() {
        return t001w;
    }

    public void setT001w(T001w t001w) {
        this.t001w = t001w;
    }

    public T001l getT001l() {
        return t001l;
    }

    public void setT001l(T001l t001l) {
        this.t001l = t001l;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public BigDecimal getLfimg1() {
        return lfimg1;
    }

    public void setLfimg1(BigDecimal lfimg1) {
        this.lfimg1 = lfimg1;
    }

    public String getMeins1() {
        return meins1;
    }

    public void setMeins1(String meins1) {
        this.meins1 = meins1;
    }

    public BigDecimal getLfimg2() {
        return lfimg2;
    }

    public void setLfimg2(BigDecimal lfimg2) {
        this.lfimg2 = lfimg2;
    }

    public String getMeins2() {
        return meins2;
    }

    public void setMeins2(String meins2) {
        this.meins2 = meins2;
    }

    public BigDecimal getLfimg() {
        return lfimg;
    }

    public void setLfimg(BigDecimal lfimg) {
        this.lfimg = lfimg;
    }

    public String getMeins() {
        return meins;
    }

    public void setMeins(String meins) {
        this.meins = meins;
    }

    public BigDecimal getDiffLfimg1() {
        return diffLfimg1;
    }

    public void setDiffLfimg1(BigDecimal diffLfimg1) {
        this.diffLfimg1 = diffLfimg1;
    }

    public BigDecimal getDiffLfimg2() {
        return diffLfimg2;
    }

    public void setDiffLfimg2(BigDecimal diffLfimg2) {
        this.diffLfimg2 = diffLfimg2;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractReceiveInfoEntity that = (AbstractReceiveInfoEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
