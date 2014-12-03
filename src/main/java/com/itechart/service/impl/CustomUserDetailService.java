package com.itechart.service.impl;

import com.itechart.model.hibernate.Employee;
import com.itechart.repository.EmployeeRepository;
import com.itechart.sessionclass.AuthenticatedEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Employee employee = employeeRepository.readEmployee(s);
        if(employee != null) {
            List<GrantedAuthority> authorities =  new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(employee.getRole().name()));
            AuthenticatedEmployee user = new AuthenticatedEmployee(employee.getLogin(), employee.getPassword(), true, true, true, authorities);
            user.setEmployeeId(employee.getId());
            user.setCompanyId(employee.getCompany().getId());
            return user;
        }else {
            throw new UsernameNotFoundException("Can't locate employee '" + s + "'");
        }
    }
}
