package com.itechart.controller;

import com.itechart.model.dto.LoginDTO;
import com.itechart.enumProperty.RoleEnum;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping(method = RequestMethod.GET, value = "/userInfo")
    @ResponseBody
    public LoginDTO currentUserInfo(){
        Logger.getLogger(UserController.class).info("Request: /EmployeeService/user/userInfo");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List authority = (List)authentication.getAuthorities();
        String stringRole = ((GrantedAuthority)authority.get(0)).getAuthority();
        String username = authentication.getName();
        return new LoginDTO(RoleEnum.valueOf(stringRole), username);
    }
}
