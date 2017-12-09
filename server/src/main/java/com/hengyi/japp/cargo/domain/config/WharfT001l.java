package com.hengyi.japp.cargo.domain.config;

import com.hengyi.japp.cargo.domain.AbstractLoggableEntity;
import com.hengyi.japp.cargo.domain.sap.T001l;
import com.hengyi.japp.cargo.domain.sap.T001lPK;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 描述： 码头
 *
 * @author jzb 2017-11-30
 */
@Entity
@IdClass(T001lPK.class)
@Table(name = "T_WHARFT001L")
@NamedQueries({
        @NamedQuery(name = "WharfT001l.queryAll", query = "SELECT o FROM WharfT001l o WHERE o.deleted=FALSE"),
})
public class WharfT001l extends AbstractLoggableEntity {
    @Id
    @Column(length = 36)
    private String werks;
    @Id
    @Column(length = 36)
    private String lgort;
    private boolean deleted;
    @NotNull
    @OneToOne
    @PrimaryKeyJoinColumns(value = {
            @PrimaryKeyJoinColumn(name = "werks", referencedColumnName = "werks"),
            @PrimaryKeyJoinColumn(name = "lgort", referencedColumnName = "lgort"),
    })
    private T001l t001l;

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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public T001l getT001l() {
        return t001l;
    }

    public void setT001l(T001l t001l) {
        this.t001l = t001l;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WharfT001l that = (WharfT001l) o;
        return Objects.equals(werks, that.werks) &&
                Objects.equals(lgort, that.lgort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(werks, lgort);
    }
}
