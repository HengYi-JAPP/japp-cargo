package com.hengyi.japp.cargo.domain.sap;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
@Entity
@Table(name = "T_SAPLFA1")
public class Lfa1 implements Serializable {
    @JsonSetter("LIFNR")
    @Id
    @Column(length = 36)
    private String lifnr;
    @JsonSetter("LAND1")
    private String land1;
    @JsonSetter("NAME1")
    private String name1;

    @JsonGetter
    public String getId() {
        return lifnr;
    }

    public String getLifnr() {
        return lifnr;
    }

    public void setLifnr(String lifnr) {
        this.lifnr = lifnr;
    }

    public String getLand1() {
        return land1;
    }

    public void setLand1(String land1) {
        this.land1 = land1;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lfa1 lfa1 = (Lfa1) o;
        return Objects.equals(lifnr, lfa1.lifnr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lifnr);
    }
}
