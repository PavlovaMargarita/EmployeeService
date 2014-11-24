package com.itechart.controller;

import com.itechart.dto.EmployeeDTO;
import com.itechart.dto.SearchResult;
import com.itechart.dto.SearchString;
import com.itechart.service.EmployeeService;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.GET, value = "/employeeList")
    @ResponseBody
    public List<EmployeeDTO> employeeList(@RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords) {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/employeeList ");
        return employeeService.readEmployeeList(currentPage - 1, pageRecords);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/employeeCount")
    @ResponseBody
    public long employeeCount() {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/employeeCount ");
        return employeeService.employeeCount();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveEmployeeCreate")
    @ResponseBody
    public Long saveEmployeeCreate(@RequestBody EmployeeDTO employeeDTO) throws IOException, SolrServerException {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/saveEmployeeCreate ");
        return employeeService.createEmployee(employeeDTO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/employeeById")
    @ResponseBody
    public EmployeeDTO readEmployee(@RequestParam("id") Long id) {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/employeeById");
        return employeeService.readEmployee(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveEmployeeUpdate")
    @ResponseBody
    public Long saveEmployeeUpdate(@RequestBody EmployeeDTO employeeDTO) throws IOException, SolrServerException {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/saveEmployeeUpdate ");
        return employeeService.updateEmployee(employeeDTO);
    }

    @ResponseBody
    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    public void uploadFile(@RequestParam(value = "photo", required = false) MultipartFile file,
                           @RequestParam(value = "idEmployee") String data) throws Exception {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/uploadPhoto ");
        Long id = Long.parseLong(data);
        employeeService.loadPhoto(file, id);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/currentEmployee")
    @ResponseBody
    public EmployeeDTO readCurrentEmployee() {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/currentEmployee");
        return employeeService.readCurrentEmployee();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/roleList")
    @ResponseBody
    public List readRoleList() {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/roleList");
        return employeeService.readRoleEnumForCurrentEmployee();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveEmployeeCreateCEO")
    @ResponseBody
    public void saveEmployeeCreateCEO(@RequestBody EmployeeDTO employeeDTO) throws IOException, SolrServerException {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/saveEmployeeCreate ");
        employeeService.createEmployeeCeo(employeeDTO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/employeeCeoByCompanyId")
    @ResponseBody
    public EmployeeDTO readEmployeeCEO(@RequestParam("companyId") Long companyId) {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/employeeCeoByCompanyId");
        return employeeService.readEmployeeCeoByCompanyId(companyId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/search")
    @ResponseBody
    public SearchResult search(@RequestBody SearchString searchValue, @RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords) {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/search");
        SearchResult searchResult = null;
        try {
            searchResult = employeeService.search(searchValue.getValue(), currentPage - 1, pageRecords);
        } catch (SolrServerException e) {
            Logger.getLogger(EmployeeController.class).debug("Solr connection pool shutdown or bad query. Stack trace:" + e.getStackTrace());
            throw new com.itechart.exception.SolrServerException();
        }
        return searchResult;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/fullRoleList")
    @ResponseBody
    public List readFullRoleList() {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/roleList");
        return employeeService.readRoleEnum();
    }

}
