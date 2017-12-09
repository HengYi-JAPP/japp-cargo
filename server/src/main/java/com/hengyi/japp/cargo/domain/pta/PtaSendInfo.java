package com.hengyi.japp.cargo.domain.pta;

import com.hengyi.japp.cargo.domain.AbstractSendInfoEntity;
import com.hengyi.japp.cargo.domain.sap.Ylips;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
@Entity
@Table(name = "T_PTASENDINFO")
public class PtaSendInfo extends AbstractSendInfoEntity {
    @NotBlank
    private String packType;
    @NotBlank
    private String transType;
    /**
     * 品种(生产线)
     */
    private String batchNo;
    /**
     * 袋数
     */
    private Integer packNo;
    @ManyToOne
    private Ylips ylips;
    @OneToOne
    @PrimaryKeyJoinColumn
    private PtaReceiveInfo receiveInfo;

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
    }

    public boolean hasYlips() {
        return ylips != null;
    }

    public PtaReceiveInfo getReceiveInfo() {
        return receiveInfo;
    }

    public void setReceiveInfo(PtaReceiveInfo receiveInfo) {
        this.receiveInfo = receiveInfo;
    }

    public Ylips getYlips() {
        return ylips;
    }

    public void setYlips(Ylips ylips) {
        this.ylips = ylips;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Integer getPackNo() {
        return packNo;
    }

    public void setPackNo(Integer packNo) {
        this.packNo = packNo;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}
