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
@IdClass(Knb1PK.class)
@Table(name = "T_SAPKNB1")
@NamedQueries({
        @NamedQuery(name = "Kna1.autocompleteByT001", query = "SELECT DISTINCT o.kna1 FROM Knb1 o WHERE (o.kna1.name1 LIKE :q OR o.kunnr LIKE :q) AND o.t001=:t001 AND o.loevm<>'X'"),
        @NamedQuery(name = "Kna1.autocomplete", query = "SELECT DISTINCT o.kna1 FROM Knb1 o WHERE (o.kna1.name1 LIKE :q OR o.kunnr LIKE :q) AND o.loevm<>'X'"),
})
public class Knb1 implements Serializable {
    @JsonSetter("KUNNR")
    @Id
    @Column(length = 36)
    private String kunnr;
    @JsonSetter("BUKRS")
    @Id
    @Column(length = 36)
    private String bukrs;
    @JsonSetter("LOEVM")
    private String loevm;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "kunnr", referencedColumnName = "kunnr")
    private Kna1 kna1;
    @ManyToOne
    @PrimaryKeyJoinColumn(name = "bukrs", referencedColumnName = "bukrs")
    private T001 t001;

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

    public String getLoevm() {
        return loevm;
    }

    public void setLoevm(String loevm) {
        this.loevm = loevm;
    }

    public Kna1 getKna1() {
        return kna1;
    }

    public void setKna1(Kna1 kna1) {
        this.kna1 = kna1;
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
        Knb1 knb1 = (Knb1) o;
        return Objects.equals(kunnr, knb1.kunnr) &&
                Objects.equals(bukrs, knb1.bukrs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kunnr, bukrs);
    }
}
