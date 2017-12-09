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
@IdClass(EkpoPK.class)
@Table(name = "T_SAPEKPO")
public class Ekpo implements Serializable {
    @JsonSetter("EBELN")
    @Id
    @Column(length = 36)
    private String ebeln;
    @JsonSetter("EBELP")
    @Id
    @Column(length = 36)
    private String ebelp;
    @JsonSetter("LOEKZ")
    @Column(length = 36)
    private String loekz;
    @JsonSetter("MATNR")
    private String matnr;
    @JsonSetter("BUKRS")
    @Column(length = 36)
    private String bukrs;
    @JsonSetter("WERKS")
    @Column(length = 36)
    private String werks;
    @JsonSetter("LGORT")
    @Column(length = 36)
    private String lgort;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "ebeln", referencedColumnName = "ebeln")
    private Ekko ekko;

    public Ekko getEkko() {
        return ekko;
    }

    public void setEkko(Ekko ekko) {
        this.ekko = ekko;
    }

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

    public String getLoekz() {
        return loekz;
    }

    public void setLoekz(String loekz) {
        this.loekz = loekz;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
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
        Ekpo ekpo = (Ekpo) o;
        return Objects.equals(ebeln, ekpo.ebeln) &&
                Objects.equals(ebelp, ekpo.ebelp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ebeln, ebelp);
    }
}
