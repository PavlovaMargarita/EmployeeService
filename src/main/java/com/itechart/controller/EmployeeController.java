package com.itechart.controller;

import com.itechart.dto.EmployeeDTO;
import com.itechart.dto.RoleDTO;
import com.itechart.dto.SexDTO;
import com.itechart.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Margarita on 20.10.2014.
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.GET, value = "/employeeList")
    @ResponseBody
    public List<EmployeeDTO> employeeList(@RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/employeeList ");
        return employeeService.readEmployeeList(currentPage - 1, pageRecords);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/employeeCount")
    @ResponseBody
    public long employeeCount(){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/employeeCount ");
        return employeeService.employeeCount();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveEmployeeCreate")
    public @ResponseBody Long saveEmployeeCorrect(@RequestBody EmployeeDTO employeeDTO){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/saveEmployeeCreate ");
        Long id = employeeService.createEmployee(employeeDTO);
        return id;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/employeeById")
    @ResponseBody
    public EmployeeDTO readEmployee(@RequestParam("id") Long id ){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/employeeById");
        EmployeeDTO employeeDTO= employeeService.readEmployee(id);
        return employeeDTO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveEmployeeUpdate")
    public @ResponseBody Long saveEmployeeUpdate(@RequestBody EmployeeDTO employeeDTO){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/saveEmployeeUpdate ");
        Long id = employeeService.updateEmployee(employeeDTO);
        return id;
    }

    @ResponseBody
    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    public void uploadFile(@RequestParam(value="photo", required=false) MultipartFile file,
                           @RequestParam(value="idEmployee") String data) throws Exception {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/uploadPhoto ");
        Long id = Long.parseLong(data);
        employeeService.loadPhoto(file, id);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/sexList")
    @ResponseBody
    public List<SexDTO> readSexList(){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/sexList");
        return employeeService.readSexEnum();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/currentEmployee")
    @ResponseBody
    public EmployeeDTO readCurrentEmployee(){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/currentEmployee");
        EmployeeDTO employeeDTO= employeeService.readCurrentEmployee();
        return employeeDTO;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/roleList")
    @ResponseBody
    public List<RoleDTO> readRoleList(){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/employee/roleList");
        return employeeService.readRoleEnum();
    }
}
