package com.itechart.dto;

import com.itechart.enumProperty.SexEnum;

/**
 * Created by Margarita on 21.10.2014.
 */
public class SexDTO {
    private SexEnum sexEnum;
    private String roleRussian;
    public SexDTO(){}

    public SexEnum getSexEnum() {
        return sexEnum;
    }

    public void setSexEnum(SexEnum sexEnum) {
        this.sexEnum = sexEnum;
    }

    public String getRoleRussian() {
        return roleRussian;
    }

    public void setRoleRussian(String roleRussian) {
        this.roleRussian = roleRussian;
    }
}
