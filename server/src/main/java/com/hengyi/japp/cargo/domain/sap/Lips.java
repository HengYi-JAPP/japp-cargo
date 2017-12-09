package com.hengyi.japp.cargo.domain.sap;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
@Entity
@IdClass(LipsPK.class)
@Table(name = "T_SAPLIPS")
public class Lips implements Serializable {
    @JsonSetter("VBELN")
    @Id
    @Column(length = 36)
    private String vbeln;
    @JsonSetter("POSNR")
    @Id
    @Column(length = 36)
    private String posnr;
    @JsonSetter("MATNR")
    private String matnr;
    @JsonSetter("ARKTX")
    private String arktx;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "vbeln", referencedColumnName = "vbeln")
    private Likp likp;

    public Likp getLikp() {
        return likp;
    }

    public void setLikp(Likp likp) {
        this.likp = likp;
    }

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

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getArktx() {
        return arktx;
    }

    public void setArktx(String arktx) {
        this.arktx = arktx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lips lips = (Lips) o;
        return Objects.equals(vbeln, lips.vbeln) &&
                Objects.equals(posnr, lips.posnr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vbeln, posnr);
    }

    public LipsPK pk() {
        LipsPK pk = new LipsPK();
        pk.setVbeln(vbeln);
        pk.setPosnr(posnr);
        return pk;
    }
}

