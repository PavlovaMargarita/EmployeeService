package com.itechart.controller;

import com.itechart.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class DispatcherController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String welcome(ModelMap model) {

        model.addAttribute("message", "Maven Web Project + Spring 3 MVC - welcome()");

        //Spring uses InternalResourceViewResolver and return back home.jsp
        return "home";

    }

    @RequestMapping(value="/user", method = RequestMethod.GET)
    public String user() {
        return "user";
    }

}