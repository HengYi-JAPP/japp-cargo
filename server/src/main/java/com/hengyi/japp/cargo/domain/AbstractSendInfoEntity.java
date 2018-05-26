package com.hengyi.japp.cargo.domain;

import com.hengyi.japp.cargo.domain.config.HeadInfo;
import com.hengyi.japp.cargo.domain.config.SupplyInfo;
import com.hengyi.japp.cargo.domain.config.TransCorp;
import com.hengyi.japp.cargo.domain.sap.Lfa1;
import org.hibernate.validator.constraints.NotBlank;
import org.jzb.share.CURDEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * 描述：
 *
 * @author jzb 2017-12-07
 */
@MappedSuperclass
public abstract class AbstractSendInfoEntity extends AbstractLoggableEntity implements CURDEntity<String> {
    @Id
    private String id;
    private boolean deleted;
    @Version
    private long version;
    @ManyToOne
    private SupplyInfo supplyInfo;
    @ManyToOne
    private HeadInfo headInfo;
    @ManyToOne
    private Lfa1 lfa1;
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date sendDate;
    @NotBlank
    private String carNo;
    private String carDriver;
    @ManyToOne
    private TransCorp transCorp;
    /**
     * 皮重
     */
    @Column(scale = 2)
    @NotNull
    private BigDecimal lfimg1;
    private String meins1 = "KG";
    /**
     * 毛重
     */
    @Column(scale = 2)
    @NotNull
    private BigDecimal lfimg2;
    private String meins2 = "KG";
    /**
     * 净重
     */
    @Column(scale = 2)
    @NotNull
    private BigDecimal lfimg;
    private String meins = "KG";
    private String note;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public SupplyInfo getSupplyInfo() {
        return supplyInfo;
    }

    public void setSupplyInfo(SupplyInfo supplyInfo) {
        this.supplyInfo = supplyInfo;
    }

    public HeadInfo getHeadInfo() {
        return headInfo;
    }

    public void setHeadInfo(HeadInfo headInfo) {
        this.headInfo = headInfo;
    }

    public Lfa1 getLfa1() {
        return lfa1;
    }

    public void setLfa1(Lfa1 lfa1) {
        this.lfa1 = lfa1;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarDriver() {
        return carDriver;
    }

    public void setCarDriver(String carDriver) {
        this.carDriver = carDriver;
    }

    public TransCorp getTransCorp() {
        return transCorp;
    }

    public void setTransCorp(TransCorp transCorp) {
        this.transCorp = transCorp;
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
        AbstractSendInfoEntity that = (AbstractSendInfoEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
