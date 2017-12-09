package com.hengyi.japp.cargo.domain.sap;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * 描述： 关联公司和供应商
 *
 * @author jzb 2017-11-30
 */
@Embeddable
public class Knb1PK implements Serializable {
    @Column(length = 36)
    @NotBlank
    private String kunnr;
    @Column(length = 36)
    @NotBlank
    private String bukrs;

    public String getKunnr() {
        return kunnr;
    }

    public void setKunnr(String kunnr) {
        this.kunnr = kunnr;
    }

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Knb1PK knb1PK = (Knb1PK) o;
        return Objects.equals(kunnr, knb1PK.kunnr) &&
                Objects.equals(bukrs, knb1PK.bukrs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kunnr, bukrs);
    }
}
