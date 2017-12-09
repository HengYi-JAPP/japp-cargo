package com.hengyi.japp.cargo.domain.sap;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
@Embeddable
public class EkpoPK implements Serializable {
    @Column(length = 36)
    @NotBlank
    private String ebeln;
    @Column(length = 36)
    @NotBlank
    private String ebelp;

    public String getEbeln() {
        return ebeln;
    }

    public void setEbeln(String ebeln) {
        this.ebeln = ebeln;
    }

    public String getEbelp() {
        return ebelp;
    }

    public void setEbelp(String ebelp) {
        this.ebelp = ebelp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EkpoPK ekpoPK = (EkpoPK) o;
        return Objects.equals(ebeln, ekpoPK.ebeln) &&
                Objects.equals(ebelp, ekpoPK.ebelp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ebeln, ebelp);
    }
}

