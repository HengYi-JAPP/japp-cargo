package com.hengyi.japp.cargo.application.command;

import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
public class PtaSendInfoUpdateCommand extends AbstractSendInfoUpdateCommand {
    @NotBlank
    private String packType;
    @NotBlank
    private String transType;
    private String batchNo;
    private Integer packNo;
    private BigDecimal littleLfimgPerPack;
    private String poundNo;

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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getPackNo() {
        return packNo;
    }

    public void setPackNo(Integer packNo) {
        this.packNo = packNo;
    }

    public BigDecimal getLittleLfimgPerPack() {
        return littleLfimgPerPack;
    }

    public void setLittleLfimgPerPack(BigDecimal littleLfimgPerPack) {
        this.littleLfimgPerPack = littleLfimgPerPack;
    }

    public String getPoundNo() {
        return poundNo;
    }

    public void setPoundNo(String poundNo) {
        this.poundNo = poundNo;
    }
}
