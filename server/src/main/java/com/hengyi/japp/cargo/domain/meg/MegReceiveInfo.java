package com.hengyi.japp.cargo.domain.meg;

import com.hengyi.japp.cargo.domain.AbstractReceiveInfoEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
@Entity
@Table(name = "T_MEGRECEIVEINFO")
public class MegReceiveInfo extends AbstractReceiveInfoEntity {
    /**
     * 提货单号
     */
    private String pickPoundNo;
    @OneToMany(mappedBy = "receiveInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MegSapReceiveInfo> sapReceiveInfos;

    public String getPickPoundNo() {
        return pickPoundNo;
    }

    public void setPickPoundNo(String pickPoundNo) {
        this.pickPoundNo = pickPoundNo;
    }

    public Set<MegSapReceiveInfo> getSapReceiveInfos() {
        return sapReceiveInfos;
    }

    public void setSapReceiveInfos(Set<MegSapReceiveInfo> sapReceiveInfos) {
        this.sapReceiveInfos = sapReceiveInfos;
    }
}
