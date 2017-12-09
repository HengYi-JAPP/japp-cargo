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
public class Lfb1PK implements Serializable {
    @Column(length = 36)
    @NotBlank
    private String lifnr;
    @Column(length = 36)
    @NotBlank
    private String bukrs;

    public String getLifnr() {
        return lifnr;
    }

    public void setLifnr(String lifnr) {
        this.lifnr = lifnr;
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
        Lfb1PK lfb1PK = (Lfb1PK) o;
        return Objects.equals(lifnr, lfb1PK.lifnr) &&
                Objects.equals(bukrs, lfb1PK.bukrs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lifnr, bukrs);
    }
}
