package com.hengyi.japp.cargo.domain.sap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static com.hengyi.japp.sap.Constant.SAP_TRUE;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
@Entity
@IdClass(Lfb1PK.class)
@Table(name = "T_SAPLFB1")
@NamedQueries({
        @NamedQuery(name = "Lfa1.autocompleteByT001", query = "SELECT DISTINCT o.lfa1 FROM Lfb1 o WHERE (o.lfa1.name1 LIKE :q OR o.lifnr LIKE :q) AND o.t001=:t001 AND o.loevm<>'X'"),
        @NamedQuery(name = "Lfa1.autocomplete", query = "SELECT DISTINCT o.lfa1 FROM Lfb1 o WHERE (o.lfa1.name1 LIKE :q OR o.lifnr LIKE :q) AND o.loevm<>'X'"),
})
public class Lfb1 implements Serializable {
    @JsonSetter("LIFNR")
    @Id
    @Column(length = 36)
    private String lifnr;
    @JsonSetter("BUKRS")
    @Id
    @Column(length = 36)
    private String bukrs;
    @JsonSetter("LOEVM")
    private String loevm;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "lifnr", referencedColumnName = "lifnr")
    private Lfa1 lfa1;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "bukrs", referencedColumnName = "bukrs")
    private T001 t001;

    @JsonIgnore
    public boolean isValid() {
        return !isDeleted();
    }

    @JsonIgnore
    public boolean isDeleted() {
        return SAP_TRUE.equals(loevm);
    }

    public String getLoevm() {
        return loevm;
    }

    public void setLoevm(String loevm) {
        this.loevm = loevm;
    }

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

    public Lfa1 getLfa1() {
        return lfa1;
    }

    public void setLfa1(Lfa1 lfa1) {
        this.lfa1 = lfa1;
    }

    public T001 getT001() {
        return t001;
    }

    public void setT001(T001 t001) {
        this.t001 = t001;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lfb1 lfb1 = (Lfb1) o;
        return Objects.equals(lifnr, lfb1.lifnr) &&
                Objects.equals(bukrs, lfb1.bukrs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lifnr, bukrs);
    }
}
