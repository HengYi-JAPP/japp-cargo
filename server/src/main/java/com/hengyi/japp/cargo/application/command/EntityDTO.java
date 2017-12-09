package com.hengyi.japp.cargo.application.command;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author jzb 2017-11-30
 */
public class EntityDTO implements Serializable {
    @NotBlank
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
