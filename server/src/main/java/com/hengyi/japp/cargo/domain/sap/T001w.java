package com.hengyi.japp.cargo.domain.sap;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 描述： 工厂
 *
 * @author jzb 2017-11-30
 */
@Entity
@Table(name = "T_SAPT001w")
@NamedQueries({
        @NamedQuery(name = "T001w.queryAll", query = "SELECT o FROM T001w o"),
})
public class T001w implements Serializable {
    @JsonSetter("WERKS")
    @Id
    @Column(length = 36)
    private String werks;
    @JsonSetter("NAME1")
    private String name1;

    @JsonGetter
    public String getId() {
        return werks;
    }

    public String getWerks() {
        return werks;
    }

    public void setWerks(String werks) {
        this.werks = werks;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }
}
