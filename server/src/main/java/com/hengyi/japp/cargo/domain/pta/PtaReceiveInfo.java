package com.hengyi.japp.cargo.domain.pta;

import com.hengyi.japp.cargo.domain.AbstractReceiveInfoEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
@Entity
@Table(name = "T_PTARECEIVEINFO")
public class PtaReceiveInfo extends AbstractReceiveInfoEntity {
    /**
     * 小榜平均值/包
     */
    @Column(scale = 2)
    private BigDecimal littleLfimgPerPack;
    /**
     * 榜单号
     */
    private String poundNo;
    @OneToMany(mappedBy = "receiveInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PtaSapReceiveInfo> sapReceiveInfos;

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

    public Set<PtaSapReceiveInfo> getSapReceiveInfos() {
        return sapReceiveInfos;
    }

    public void setSapReceiveInfos(Set<PtaSapReceiveInfo> sapReceiveInfos) {
        this.sapReceiveInfos = sapReceiveInfos;
    }
}
