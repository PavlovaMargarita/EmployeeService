package com.itechart.controller;

import com.itechart.dto.EmployeeDTO;
import com.itechart.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Margarita on 20.10.2014.
 */
@Controller
@RequestMapping("/EmployeeService/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.GET, value = "/employeeList")
    @ResponseBody
    public List<EmployeeDTO> employeeList(){
        Logger.getLogger(AddressController.class).info("Request /EmployeeService/employee/employeeList ");
        return employeeService.readEmployeeList();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveEmployeeCreate")
    public @ResponseBody void saveEmployeeCorrect(@RequestBody EmployeeDTO employeeDTO){
        Logger.getLogger(AddressController.class).info("Request /EmployeeService/employee/saveEmployeeCreate ");
        employeeService.createEmployee(employeeDTO);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/employeeById")
    @ResponseBody
    public EmployeeDTO readEmployee(@RequestParam("id") Long id ){
        Logger.getLogger(AddressController.class).info("Request /EmployeeService/employee/employeeById");
        EmployeeDTO employeeDTO= employeeService.readEmployee(id);
        return employeeDTO;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveEmployeeUpdate")
    public @ResponseBody void saveEmployeeUpdate(@RequestBody EmployeeDTO employeeDTO){
        Logger.getLogger(AddressController.class).info("Request /EmployeeService/employee/saveEmployeeUpdate ");
        employeeService.updateEmployee(employeeDTO);
    }
}
