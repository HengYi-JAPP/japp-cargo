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
public class T001lPK implements Serializable {
    @Column(length = 36)
    @NotBlank
    private String werks;
    @Column(length = 36)
    @NotBlank
    private String lgort;

    public T001lPK(String werks, String lgort) {
        this.werks = werks;
        this.lgort = lgort;
    }

    public T001lPK() {
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getLgort() {
        return lgort;
    }

    public void setLgort(String lgort) {
        this.lgort = lgort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        T001lPK t001lPK = (T001lPK) o;
        return Objects.equals(werks, t001lPK.werks) &&
                Objects.equals(lgort, t001lPK.lgort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(werks, lgort);
    }
}

