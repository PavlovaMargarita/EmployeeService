package com.itechart.dto;

import com.itechart.enumProperty.RoleEnum;

/**
 * Created by marharyta.pavlova on 14.11.2014.
 */
public class RoleDTO {
    private RoleEnum roleEnum;
    private String roleRussian;
    public RoleDTO(){}

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public String getRoleRussian() {
        return roleRussian;
    }

    public void setRoleRussian(String roleRussian) {
        this.roleRussian = roleRussian;
    }
}
