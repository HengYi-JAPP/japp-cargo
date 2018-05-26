package com.hengyi.japp.cargo.domain.meg;

import com.hengyi.japp.cargo.domain.AbstractSendInfoEntity;
import com.hengyi.japp.cargo.domain.sap.T001l;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
@Entity
@Table(name = "T_MEGSENDINFO")
@NamedQueries({
        @NamedQuery(name = "MegSendInfo.querySendInfo", query = "SELECT o FROM MegSendInfo o WHERE o.sendDate=:sendDate AND o.creator.id=:creatorId AND o.deleted=FALSE"),
})
public class MegSendInfo extends AbstractSendInfoEntity {
    /**
     * 0 meg 默认
     * 1 煤制
     */
    private int megType;
    @ManyToOne
    private T001l wharf;
    @OneToOne
    @PrimaryKeyJoinColumn
    private MegReceiveInfo receiveInfo;

    public int getMegType() {
        return megType;
    }

    public void setMegType(int megType) {
        this.megType = megType;
    }

    public T001l getWharf() {
        return wharf;
    }

    public void setWharf(T001l wharf) {
        this.wharf = wharf;
    }

    public MegReceiveInfo getReceiveInfo() {
        return receiveInfo;
    }

    public void setReceiveInfo(MegReceiveInfo receiveInfo) {
        this.receiveInfo = receiveInfo;
    }
}
