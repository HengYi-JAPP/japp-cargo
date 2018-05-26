package com.hengyi.japp.cargo.application.command;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 *
 * @author jzb 2017-12-01
 */
public class HeadInfoUpdateCommand implements Serializable {
    @NotBlank
    private String name;
    @NotNull
    @Size(min = 1)
    private List<EntityDTO> t001s;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EntityDTO> getT001s() {
        return t001s;
    }

    public void setT001s(List<EntityDTO> t001s) {
        this.t001s = t001s;
    }
}
