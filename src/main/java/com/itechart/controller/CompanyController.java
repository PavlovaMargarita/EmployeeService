package com.itechart.controller;

import com.itechart.dto.AddressDTO;
import com.itechart.dto.DepartmentDTO;
import com.itechart.dto.PositionInCompanyDTO;
import com.itechart.service.CompanyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Margarita on 29.10.2014.
 */
@Controller
@RequestMapping("/EmployeeService/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET, value = "/positionInCompanyList")
    @ResponseBody
    public List<PositionInCompanyDTO> positionList(){
        Logger.getLogger(CompanyController.class).info("Request: /EmployeeService/positionInCompany/positionInCompanyList ");
        return companyService.readPositionInCompanyList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/departmentList")
    @ResponseBody
    public List<DepartmentDTO> departmentList(){
        Logger.getLogger(CompanyController.class).info("Request: /EmployeeService/company/departmentList ");
        return companyService.readDepartmentList();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/addressList")
    @ResponseBody
    public List<AddressDTO> addressList(@RequestParam("departmentId") Long id ){
        Logger.getLogger(CompanyController.class).info("Request: /EmployeeService/company/addressList, parameter id = " + id);
        return companyService.readAddressList(id);
    }
}
