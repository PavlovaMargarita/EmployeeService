package com.itechart.params;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class CurrentEmployeeParam {
    public static Long getCurrentCompanyId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List authority = (List)authentication.getAuthorities();
        String companyId = ((GrantedAuthority)authority.get(1)).getAuthority();
        return Long.parseLong(companyId.substring(10));
    }

    public static Long getCurrentEmployeeId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List authority = (List)authentication.getAuthorities();
        String employeeId = ((GrantedAuthority)authority.get(2)).getAuthority();
        return Long.parseLong(employeeId.substring(11));
    }
}
