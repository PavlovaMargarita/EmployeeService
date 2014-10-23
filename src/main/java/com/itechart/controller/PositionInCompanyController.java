package com.itechart.controller;

import com.itechart.dto.PositionInCompanyDTO;
import com.itechart.service.PositionInCompanyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Margarita on 20.10.2014.
 */

@Controller
@RequestMapping("/EmployeeService/positionInCompany")

public class PositionInCompanyController {
    @Autowired
    private PositionInCompanyService positionInCompanyService;

    @RequestMapping(method = RequestMethod.GET, value = "/positionList")
    @ResponseBody
    public List<PositionInCompanyDTO> positionList(){
        Logger.getLogger(AddressController.class).info("Request /EmployeeService/positionInCompany/positionList ");
        return positionInCompanyService.readPositionInCompanyList();
    }
}
