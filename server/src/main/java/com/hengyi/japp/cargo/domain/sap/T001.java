package com.hengyi.japp.cargo.domain.sap;

import com.fasterxml.jackson.annotation.JsonGetter;
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
@Table(name = "T_SAPT001")
@NamedQueries({
        @NamedQuery(name = "T001.queryAll", query = "SELECT DISTINCT o FROM T001 o INNER JOIN T001k o2 ON o.bukrs=o2.bukrs"),
})
public class T001 implements Serializable {
    @JsonSetter("BUKRS")
    @Id
    @Column(length = 36)
    private String bukrs;
    @JsonSetter("BUTXT")
    private String butxt;

    @JsonGetter
    public String getId() {
        return bukrs;
    }

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }

    public String getButxt() {
        return butxt;
    }

    public void setButxt(String butxt) {
        this.butxt = butxt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        T001 t001 = (T001) o;
        return Objects.equals(bukrs, t001.bukrs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bukrs);
    }
}
