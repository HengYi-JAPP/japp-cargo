package com.hengyi.japp.cargo.domain.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hengyi.japp.cargo.domain.AbstractLoggableEntity;
import com.hengyi.japp.cargo.domain.sap.T001;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * 描述： 物流公司
 *
 * @author jzb 2017-11-30
 */
@Entity
@Table(name = "T_TRANSCORP")
@NamedQueries({
        @NamedQuery(name = "TransCorp.queryAll", query = "SELECT o FROM TransCorp o WHERE o.deleted=FALSE"),
        @NamedQuery(name = "TransCorp.queryByT001", query = "SELECT o FROM TransCorp o WHERE :t001 MEMBER OF o.t001s AND o.deleted=FALSE"),
})
public class TransCorp extends AbstractLoggableEntity {
    @Id
    private String id;
    @NotBlank
    private String name;
    private boolean deleted;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "T_TRANSCORP_T_SAPT001")
    private Set<T001> t001s;

    public Set<T001> getT001s() {
        return t001s;
    }

    public void setT001s(Set<T001> t001s) {
        this.t001s = t001s;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransCorp transCorp = (TransCorp) o;
        return Objects.equals(id, transCorp.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
