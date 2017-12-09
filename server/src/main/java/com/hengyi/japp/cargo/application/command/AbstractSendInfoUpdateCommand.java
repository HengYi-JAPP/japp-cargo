package com.hengyi.japp.cargo.application.command;

import com.hengyi.japp.cargo.domain.sap.T001lPK;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 描述：
 *
 * @author jzb 2017-12-07
 */
public abstract class AbstractSendInfoUpdateCommand implements Serializable {
    private EntityDTO lfa1;
    @NotNull
    private Date sendDate;
    @NotBlank
    private String carNo;
    private String carDriver;
    private EntityDTO transCorp;
    @NotNull
    @Min(0)
    private BigDecimal sendLfimg1;
    @NotNull
    @Min(0)
    private BigDecimal sendLfimg2;
    private String sendNote;

    @NotNull
    private Date receiveDate;
    @NotNull
    private T001lPK receiveT001l;
    @NotNull
    @Min(0)
    private BigDecimal receiveLfimg1;
    @NotNull
    @Min(0)
    private BigDecimal receiveLfimg2;
    @NotNull
    private BigDecimal diffLfimg1;
    @NotNull
    private BigDecimal diffLfimg2;
    private String receiveNote;

    private List<SapReceiveInfoDTO> sapReceiveInfos;

    public EntityDTO getLfa1() {
        return lfa1;
    }

    public void setLfa1(EntityDTO lfa1) {
        this.lfa1 = lfa1;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getCarNo() {
        return StringUtils.upperCase(carNo);
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

    public EntityDTO getTransCorp() {
        return transCorp;
    }

    public void setTransCorp(EntityDTO transCorp) {
        this.transCorp = transCorp;
    }

    public BigDecimal getSendLfimg1() {
        return sendLfimg1;
    }

    public void setSendLfimg1(BigDecimal sendLfimg1) {
        this.sendLfimg1 = sendLfimg1;
    }

    public BigDecimal getSendLfimg2() {
        return sendLfimg2;
    }

    public void setSendLfimg2(BigDecimal sendLfimg2) {
        this.sendLfimg2 = sendLfimg2;
    }

    public String getSendNote() {
        return sendNote;
    }

    public void setSendNote(String sendNote) {
        this.sendNote = sendNote;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public T001lPK getReceiveT001l() {
        return receiveT001l;
    }

    public void setReceiveT001l(T001lPK receiveT001l) {
        this.receiveT001l = receiveT001l;
    }

    public BigDecimal getReceiveLfimg1() {
        return receiveLfimg1;
    }

    public void setReceiveLfimg1(BigDecimal receiveLfimg1) {
        this.receiveLfimg1 = receiveLfimg1;
    }

    public BigDecimal getReceiveLfimg2() {
        return receiveLfimg2;
    }

    public void setReceiveLfimg2(BigDecimal receiveLfimg2) {
        this.receiveLfimg2 = receiveLfimg2;
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

    public String getReceiveNote() {
        return receiveNote;
    }

    public void setReceiveNote(String receiveNote) {
        this.receiveNote = receiveNote;
    }

    public List<SapReceiveInfoDTO> getSapReceiveInfos() {
        return sapReceiveInfos;
    }

    public void setSapReceiveInfos(List<SapReceiveInfoDTO> sapReceiveInfos) {
        this.sapReceiveInfos = sapReceiveInfos;
    }
}
