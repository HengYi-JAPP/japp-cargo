package com.hengyi.japp.cargo.domain.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hengyi.japp.cargo.domain.AbstractLoggableEntity;
import com.hengyi.japp.cargo.domain.sap.T001;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Set;

/**
 * 描述： 抬头
 *
 * @author jzb 2017-12-21
 */
@Entity
@Table(name = "T_HEADINFO")
@NamedQueries({
        @NamedQuery(name = "HeadInfo.queryAll", query = "SELECT o FROM HeadInfo o WHERE o.deleted=FALSE"),
        @NamedQuery(name = "HeadInfo.queryByT001", query = "SELECT o FROM HeadInfo o WHERE :t001 MEMBER OF o.t001s AND o.deleted=FALSE"),
})
public class HeadInfo extends AbstractLoggableEntity {
    @Id
    private String id;
    @NotBlank
    private String name;
    private boolean deleted;
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "T_HEADINFO_T_SAPT001")
    private Set<T001> t001s;

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

    public Set<T001> getT001s() {
        return t001s;
    }

    public void setT001s(Set<T001> t001s) {
        this.t001s = t001s;
    }
}
