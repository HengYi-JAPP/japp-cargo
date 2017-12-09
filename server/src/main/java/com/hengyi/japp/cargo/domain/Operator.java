package com.hengyi.japp.cargo.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.jzb.share.CURDEntity;

import javax.persistence.*;

/**
 * Created by jzb on 16-10-20.
 */
@Entity
@Table(name = "T_OPERATOR")
@NamedQueries({
        @NamedQuery(name = "Operator.findByOaId", query = "SELECT o FROM Operator o WHERE o.oaId=:oaId AND o.deleted=FALSE"),
        @NamedQuery(name = "Operator.findByHrId", query = "SELECT o FROM Operator o WHERE o.hrId=:hrId AND o.deleted=FALSE"),
        @NamedQuery(name = "Operator.count", query = "SELECT COUNT(o) FROM Operator o WHERE o.deleted=FALSE"),
        @NamedQuery(name = "Operator.queryAll", query = "SELECT o FROM Operator o WHERE o.deleted=FALSE"),
})
public class Operator implements CURDEntity<String> {
    @Id
    @Column(length = 36)
    private String id;
    private boolean deleted;
    @NotBlank
    @Column(length = 50)
    private String name;
    private String oaId;
    private String hrId;
    private String avatar;
    private boolean admin;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOaId() {
        return oaId;
    }

    public void setOaId(String oaId) {
        this.oaId = oaId;
    }

    public String getHrId() {
        return hrId;
    }

    public void setHrId(String hrId) {
        this.hrId = hrId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
