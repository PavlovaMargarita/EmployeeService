package com.itechart.controller;

import com.itechart.model.dto.CountryDTO;
import com.itechart.service.CountryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/country")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @RequestMapping(method = RequestMethod.GET, value = "/countryList")
    @ResponseBody
    public List<CountryDTO> countryList(){
        Logger.getLogger(CountryController.class).info("Request: /EmployeeService/country/countryList ");
        return countryService.readCountryList();
    }
}
