package com.itechart.controller;

import com.itechart.dto.*;
import com.itechart.service.CompanyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.sql.Date;

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

    @RequestMapping(method = RequestMethod.GET, value = "/companyList")
    @ResponseBody
    public List<CompanyDTO> companyList(@RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords){
        Logger.getLogger(CompanyController.class).info("Request: /EmployeeService/company/companyList");
        return companyService.readCompanyList(currentPage - 1, pageRecords);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/companyCount")
    @ResponseBody
    public long companyCount(){
        Logger.getLogger(CompanyController.class).info("Request: /EmployeeService/company/companyCount");
        return companyService.companyCount();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/companyById")
    @ResponseBody
    public CompanyDTO readCompany(@RequestParam("id") Long id ){
        Logger.getLogger(CompanyController.class).info("Request: /EmployeeService/company/companyById");
        CompanyDTO companyDTO = companyService.readCompany(id);
        return companyDTO;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/companyStatusList")
    @ResponseBody
    public List<CompanyStatusDTO> readCompanyStatusList(){
        Logger.getLogger(CompanyController.class).info("Request: /EmployeeService/company/companyStatusList");
        return companyService.readCompanyStatusEnum();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveCompanyUpdate")
    public @ResponseBody void saveCompanyUpdate(@RequestBody CompanyDTO companyDTO){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/saveCompanyUpdate ");
        companyService.updateCompany(companyDTO);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveCompanyCreate")
    public @ResponseBody void saveCompanyCreate(@RequestBody CompanyDTO companyDTO){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/saveCompanyCreate ");
        companyService.createCompany(companyDTO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/dateBoundaryRefill")
    @ResponseBody
    public Date getDateBoundaryRefill(){
        Logger.getLogger(CompanyController.class).info("Request: /EmployeeService/company/dateBoundaryRefill");
        return companyService.getDateBoundaryRefill();
    }
}
