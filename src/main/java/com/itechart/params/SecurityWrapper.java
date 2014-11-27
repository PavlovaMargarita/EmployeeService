package com.itechart.params;

import com.itechart.sessionclass.AuthenticatedEmployee;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityWrapper {
    public static Long getCurrentCompanyId() {
        return getAuthenticatedEmployee().getCompanyId();
    }

    public static Long getCurrentEmployeeId() {
        return getAuthenticatedEmployee().getEmployeeId();
    }

    private static AuthenticatedEmployee getAuthenticatedEmployee() {
        return (AuthenticatedEmployee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
