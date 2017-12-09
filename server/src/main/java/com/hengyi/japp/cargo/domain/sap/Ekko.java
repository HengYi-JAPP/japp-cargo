package com.hengyi.japp.cargo.domain.sap;

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
@Table(name = "T_SAPEKKO")
public class Ekko implements Serializable {
    @JsonSetter("EBELN")
    @Id
    @Column(length = 36)
    private String ebeln;
    @JsonSetter("BUKRS")
    @Column(length = 36)
    private String bukrs;
    @JsonSetter("LIFNR")
    @Column(length = 36)
    private String lifnr;
    @JsonSetter("LOEKZ")
    @Column(length = 36)
    private String loekz;

    public String getEbeln() {
        return ebeln;
    }

    public void setEbeln(String ebeln) {
        this.ebeln = ebeln;
    }

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }

    public String getLifnr() {
        return lifnr;
    }

    public void setLifnr(String lifnr) {
        this.lifnr = lifnr;
    }

    public String getLoekz() {
        return loekz;
    }

    public void setLoekz(String loekz) {
        this.loekz = loekz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ekko ekko = (Ekko) o;
        return Objects.equals(ebeln, ekko.ebeln);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ebeln);
    }
}
