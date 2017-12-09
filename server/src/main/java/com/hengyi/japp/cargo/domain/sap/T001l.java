package com.hengyi.japp.cargo.domain.sap;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 描述： 库存地
 *
 * @author jzb 2017-11-30
 */
@Entity
@IdClass(T001lPK.class)
@Table(name = "T_SAPT001l")
@NamedQueries({
        @NamedQuery(name = "T001l.queryAll", query = "SELECT o FROM T001l o"),
})
public class T001l implements Serializable {
    @JsonSetter("WERKS")
    @Id
    @Column(length = 36)
    private String werks;
    @JsonSetter("LGORT")
    @Id
    @Column(length = 36)
    private String lgort;
    @JsonSetter("LGOBE")
    private String lgobe;

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

    public String getLgobe() {
        return lgobe;
    }

    public void setLgobe(String lgobe) {
        this.lgobe = lgobe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        T001l t001l = (T001l) o;
        return Objects.equals(werks, t001l.werks) &&
                Objects.equals(lgort, t001l.lgort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(werks, lgort);
    }
}
