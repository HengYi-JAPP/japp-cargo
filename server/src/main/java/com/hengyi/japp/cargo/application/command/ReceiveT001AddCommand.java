package com.hengyi.japp.cargo.application.command;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 描述：
 *
 * @author jzb 2017-12-01
 */
public class ReceiveT001AddCommand implements Serializable {
    @NotNull
    private EntityDTO t001;
    private EntityDTO kna1;
    private EntityDTO lfa1;

    public EntityDTO getT001() {
        return t001;
    }

    public void setT001(EntityDTO t001) {
        this.t001 = t001;
    }

    public EntityDTO getKna1() {
        return kna1;
    }

    public void setKna1(EntityDTO kna1) {
        this.kna1 = kna1;
    }

    public EntityDTO getLfa1() {
        return lfa1;
    }

    public void setLfa1(EntityDTO lfa1) {
        this.lfa1 = lfa1;
    }
}
