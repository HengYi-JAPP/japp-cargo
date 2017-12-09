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
public class LipsPK implements Serializable {
    @Column(length = 36)
    @NotBlank
    private String vbeln;
    @Column(length = 36)
    @NotBlank
    private String posnr;

    public String getVbeln() {
        return vbeln;
    }

    public void setVbeln(String vbeln) {
        this.vbeln = vbeln;
    }

    public String getPosnr() {
        return posnr;
    }

    public void setPosnr(String posnr) {
        this.posnr = posnr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LipsPK lipsPK = (LipsPK) o;
        return Objects.equals(vbeln, lipsPK.vbeln) &&
                Objects.equals(posnr, lipsPK.posnr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vbeln, posnr);
    }
}

