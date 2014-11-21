package com.itechart.service.impl;

import com.itechart.model.Employee;
import com.itechart.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
        Employee user = employeeRepository.readEmployee(s);
        if(user != null) {
            List<GrantedAuthority> authorities = buildUserAuthority(user);
            return buildUserForAuthentication(user, authorities);
        }else {
            throw new UsernameNotFoundException("Can't locate employee '" + s + "'");
        }
    }

    private List<GrantedAuthority> buildUserAuthority(Employee employee) {

        List<GrantedAuthority> setAuths = new ArrayList<>();

        // Build employee's authorities
        setAuths.add(new SimpleGrantedAuthority(employee.getRole().name()));
        String companyId = "companyId=";
        setAuths.add(new SimpleGrantedAuthority(companyId + employee.getCompany().getId()));
        String employeeId = "employeeId=";
        setAuths.add(new SimpleGrantedAuthority(employeeId + employee.getId()));
        return new ArrayList<>(setAuths);
    }

    private User buildUserForAuthentication(Employee user,
                                            List<GrantedAuthority> authorities) {
        System.out.println(user.getLogin());
        return new User(user.getLogin(), user.getPassword(),
                true, true, true, true, authorities);
    }
}
