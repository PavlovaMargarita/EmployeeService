package com.itechart.sessionclass;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthenticatedEmployee extends org.springframework.security.core.userdetails.User {

    private Long employeeId;
    private Long companyId;

    public AuthenticatedEmployee(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, Collection<? extends GrantedAuthority> authorities)
            throws IllegalArgumentException {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, true, authorities);
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
