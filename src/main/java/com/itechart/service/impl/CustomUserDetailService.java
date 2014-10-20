package com.itechart.service.impl;

import com.itechart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Margarita on 20.10.2014.
 */
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        com.itechart.model.User user = userRepository.readUser(s);
        if(user != null) {
            List<GrantedAuthority> authorities = buildUserAuthority(user);
            return buildUserForAuthentication(user, authorities);
        }else {
            throw new UsernameNotFoundException("Can't locate user '" + s + "'");
        }
    }

    private List<GrantedAuthority> buildUserAuthority(com.itechart.model.User user) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        setAuths.add(new SimpleGrantedAuthority(user.getRole().name()));

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

    private User buildUserForAuthentication(com.itechart.model.User user,
                                            List<GrantedAuthority> authorities) {
        System.out.println(user.getLogin());
        return new User(user.getLogin(), user.getPassword(),
                true, true, true, true, authorities);
    }
}
