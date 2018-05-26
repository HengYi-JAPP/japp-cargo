package com.hengyi.japp.cargo.application.command;

import com.hengyi.japp.cargo.domain.sap.EkpoPK;
import com.hengyi.japp.cargo.domain.sap.T001lPK;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
public class DailyDetailExportTokenCommand implements Serializable {
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    private boolean allT001s;
    private Set<EntityDTO> t001s;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
