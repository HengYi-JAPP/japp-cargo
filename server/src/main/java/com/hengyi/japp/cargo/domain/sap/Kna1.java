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
@Table(name = "T_SAPKNA1")
public class Kna1 implements Serializable {
    @JsonSetter("KUNNR")
    @Id
    @Column(length = 36)
    private String kunnr;
    @JsonSetter("LAND1")
    private String land1;
    @JsonSetter("NAME1")
    private String name1;

    @JsonGetter
    public String getId() {
        return kunnr;
    }

    public String getKunnr() {
        return kunnr;
    }

    public void setKunnr(String kunnr) {
        this.kunnr = kunnr;
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
        Kna1 kna1 = (Kna1) o;
        return Objects.equals(kunnr, kna1.kunnr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kunnr);
    }

}
