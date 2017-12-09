package com.hengyi.japp.cargo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hengyi.japp.cargo.domain.sap.T001;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * 描述：
 *
 * @author jzb 2017-12-02
 */
@Entity
@Table(name = "T_OPERATORPERMISSION")
public class OperatorPermission implements Serializable {
    @Id
    private String id;
    @JsonIgnore
    @OneToOne
    @PrimaryKeyJoinColumn
    private Operator operator;
    @ManyToOne
    private T001 defaultReceiveT001;
    private boolean allT001s;
    @ManyToMany
    @JoinTable(name = "T_OPERATORPERMISSION_T_SAPT001")
    private Set<T001> t001s;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public T001 getDefaultReceiveT001() {
        return defaultReceiveT001;
    }

    public void setDefaultReceiveT001(T001 defaultReceiveT001) {
        this.defaultReceiveT001 = defaultReceiveT001;
    }

    public boolean isAllT001s() {
        return allT001s;
    }

    public void setAllT001s(boolean allT001s) {
        this.allT001s = allT001s;
    }

    public Set<T001> getT001s() {
        return t001s;
    }

    public void setT001s(Set<T001> t001s) {
        this.t001s = t001s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperatorPermission that = (OperatorPermission) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
