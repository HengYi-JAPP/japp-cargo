package com.hengyi.japp.cargo.domain.config;

import com.hengyi.japp.cargo.domain.AbstractLoggableEntity;
import com.hengyi.japp.cargo.domain.sap.Kna1;
import com.hengyi.japp.cargo.domain.sap.Lfa1;
import com.hengyi.japp.cargo.domain.sap.T001;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * 描述： 收货公司
 *
 * @author jzb 2017-12-01
 */
@Entity
@Table(name = "T_RECEIVET001")
@NamedQueries({
        @NamedQuery(name = "ReceiveT001.queryAll", query = "SELECT o FROM ReceiveT001 o WHERE o.deleted=FALSE"),
})
public class ReceiveT001 extends AbstractLoggableEntity {
    @Id
    @Column(length = 36)
    private String bukrs;
    private boolean deleted;
    @OneToOne
    @PrimaryKeyJoinColumn
    @NotNull
    private T001 t001;
    /**
     * 作为客户时候的 客户编码
     */
    @ManyToOne
    private Kna1 kna1;
    /**
     * 作为供应商时候的 供应商编码
     */
    @ManyToOne
    private Lfa1 lfa1;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }

    public T001 getT001() {
        return t001;
    }

    public void setT001(T001 t001) {
        this.t001 = t001;
    }

    public Kna1 getKna1() {
        return kna1;
    }

    public void setKna1(Kna1 kna1) {
        this.kna1 = kna1;
    }

    public Lfa1 getLfa1() {
        return lfa1;
    }

    public void setLfa1(Lfa1 lfa1) {
        this.lfa1 = lfa1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiveT001 that = (ReceiveT001) o;
        return Objects.equals(bukrs, that.bukrs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bukrs);
    }
}
