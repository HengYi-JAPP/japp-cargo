package com.hengyi.japp.cargo.application.command;

import com.hengyi.japp.cargo.domain.sap.T001lPK;

import javax.validation.constraints.NotNull;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
public class MegSendInfoUpdateCommand extends AbstractSendInfoUpdateCommand {
    private int megType;
    private T001lPK wharf;
    private String pickPoundNo;

    public T001lPK getWharf() {
        return wharf;
    }

    public int getMegType() {
        return megType;
    }

    public void setMegType(int megType) {
        this.megType = megType;
    }

    public void setWharf(T001lPK wharf) {
        this.wharf = wharf;
    }

    public String getPickPoundNo() {
        return pickPoundNo;
    }

    public void setPickPoundNo(String pickPoundNo) {
        this.pickPoundNo = pickPoundNo;
    }
}
