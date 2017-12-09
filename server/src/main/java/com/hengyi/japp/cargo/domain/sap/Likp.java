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
@Table(name = "T_SAPLIKP")
public class Likp implements Serializable {
    @JsonSetter("VBELN")
    @Id
    @Column(length = 36)
    private String vbeln;
    @JsonSetter("KUNNR")
    @Column(length = 36)
    private String kunnr;

    @JsonGetter
    public String getId() {
        return vbeln;
    }

    public String getVbeln() {
        return vbeln;
    }

    public void setVbeln(String vbeln) {
        this.vbeln = vbeln;
    }

    public String getKunnr() {
        return kunnr;
    }

    public void setKunnr(String kunnr) {
        this.kunnr = kunnr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Likp likp = (Likp) o;
        return Objects.equals(vbeln, likp.vbeln);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vbeln);
    }
}
