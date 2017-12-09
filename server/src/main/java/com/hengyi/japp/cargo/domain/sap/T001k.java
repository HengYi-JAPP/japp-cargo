package com.hengyi.japp.cargo.domain.sap;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
@Entity
@Table(name = "T_SAPT001k")
@NamedQueries({
        @NamedQuery(name = "T001k.queryAll", query = "SELECT o FROM T001k o"),
})
public class T001k implements Serializable {
    @JsonSetter("BWKEY")
    @Id
    @Column(length = 36)
    private String bwkey;
    @JsonSetter("BUKRS")
    @NotBlank
    @Column(length = 36)
    private String bukrs;

    @JsonGetter
    public String getId() {
        return bwkey;
    }

    public String getBwkey() {
        return bwkey;
    }

    public void setBwkey(String bwkey) {
        this.bwkey = bwkey;
    }

    public String getBukrs() {
        return bukrs;
    }

    public void setBukrs(String bukrs) {
        this.bukrs = bukrs;
    }
}
