package com.hengyi.japp.cargo.domain.sap;

import com.fasterxml.jackson.annotation.JsonSetter;
import org.jzb.J;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

import static com.hengyi.japp.sap.Constant.SAP_TRUE;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */

@Entity
@Table(name = "T_SAPYLIPS")
public class Ylips implements Comparable<Ylips>, Serializable {
    @JsonSetter("POUND_NO")
    @Id
    @Column(length = 36)
    private String id;
    @ManyToOne
    private Lips lips;
    /**
     * 1 = 槽车
     * 2 = 1.1吨新袋
     * 3 = 1.1吨旧袋
     * 4 = 非正常
     * 5 = 1.2吨新袋
     */
    @JsonSetter("PACK_TYPE")
    @Column(length = 4)
    private String packType;
    /**
     * -1 = 船运
     * 1 = 槽车
     * 2 = 平板车
     * 3 = 集装箱
     */
    @JsonSetter("TRANS_TYPE")
    @Column(length = 4)
    private String transType;
    @JsonSetter("CAR_NO")
    private String carNo;
    @JsonSetter("BATCH_NO")
    private String batchNo;
    /**
     * 皮重
     */
    @JsonSetter("LFIMG1")
    @Column(scale = 2)
    private BigDecimal lfimg1;
    @JsonSetter("MEINS1")
    private String meins1;
    /**
     * 毛重
     */
    @JsonSetter("LFIMG2")
    @Column(scale = 2)
    private BigDecimal lfimg2;
    @JsonSetter("MEINS2")
    private String meins2;
    /**
     * 净重
     */
    @JsonSetter("LFIMG")
    @Column(scale = 2)
    private BigDecimal lfimg;
    @JsonSetter("MEINS")
    private String meins;
    @JsonSetter("NOTE")
    private String note;
    @JsonSetter("PHD_ZVBELN")
    @Column(length = 36)
    private String phdZvbeln;
    @JsonSetter("PHD_POSNR")
    @Column(length = 36)
    private String phdPosnr;
    @JsonSetter("ERDAT")
    @Temporal(TemporalType.DATE)
    private Date erdat;
    @JsonSetter("ERZET")
    @Temporal(TemporalType.TIME)
    private Date erzet;
    @JsonSetter("DELETE_FLAG")
    private String deleteFlag;

    public boolean isDeleted() {
        return SAP_TRUE.equals(deleteFlag);
    }

    public boolean isValid() {
        return !isDeleted();
    }

    public Lips getLips() {
        return lips;
    }

    public void setLips(Lips lips) {
        this.lips = lips;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getPhdZvbeln() {
        return phdZvbeln;
    }

    public void setPhdZvbeln(String phdZvbeln) {
        this.phdZvbeln = phdZvbeln;
    }

    public String getPhdPosnr() {
        return phdPosnr;
    }

    public void setPhdPosnr(String phdPosnr) {
        this.phdPosnr = phdPosnr;
    }

    public Date getErdat() {
        return erdat;
    }

    public void setErdat(Date erdat) {
        this.erdat = erdat;
    }

    public Date getErzet() {
        return erzet;
    }

    public void setErzet(Date erzet) {
        this.erzet = erzet;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ylips pound = (Ylips) o;
        return Objects.equals(id, pound.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Ylips o) {
        LocalDate ld = J.localDate(erdat);
        LocalTime lt = J.localTime(erzet);
        LocalDateTime ldt = ld.atTime(lt);

        LocalDate ld_o = J.localDate(o.erdat);
        LocalTime lt_o = J.localTime(o.erzet);
        LocalDateTime ldt_o = ld_o.atTime(lt_o);
        return ldt.compareTo(ldt_o);
    }
}

