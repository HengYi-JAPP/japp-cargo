package com.hengyi.japp.cargo.domain;

import com.hengyi.japp.cargo.domain.config.TransCorp;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 描述：
 *
 * @author jzb 2017-12-06
 */
@Entity
@Table(name = "T_CARNOINFOLOG")
@NamedQueries({
        @NamedQuery(name = "CarNoInfoLog.autocomplete", query = "SELECT o FROM CarNoInfoLog o WHERE o.carNo LIKE :q"),
})
public class CarNoInfoLog implements Serializable {
    @Id
    private String carNo;
    private String carDriver;
    @ManyToOne
    private TransCorp transCorp;

    public String getCarNo() {
        return carNo;
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

    public TransCorp getTransCorp() {
        return transCorp;
    }

    public void setTransCorp(TransCorp transCorp) {
        this.transCorp = transCorp;
    }
}
