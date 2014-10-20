package com.itechart.dto;

import com.itechart.enumProperty.RoleEnum;

public class LoginDTO {

    private String login;
    private RoleEnum role;

    public LoginDTO(RoleEnum role, String login) {
        this.role = role;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
