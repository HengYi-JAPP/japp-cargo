package com.hengyi.japp.cargo.application.command;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * 描述：
 *
 * @author jzb 2017-12-02
 */
public class OperatorPermissionUpdateCommand implements Serializable {
    private boolean allT001s;
    @NotNull
    private EntityDTO defaultReceiveT001;
    private Set<EntityDTO> t001s;

    public EntityDTO getDefaultReceiveT001() {
        return defaultReceiveT001;
    }

    public void setDefaultReceiveT001(EntityDTO defaultReceiveT001) {
        this.defaultReceiveT001 = defaultReceiveT001;
    }

    public boolean isAllT001s() {
        return allT001s;
    }

    public void setAllT001s(boolean allT001s) {
        this.allT001s = allT001s;
    }

    public Set<EntityDTO> getT001s() {
        return t001s;
    }

    public void setT001s(Set<EntityDTO> t001s) {
        this.t001s = t001s;
    }
}
