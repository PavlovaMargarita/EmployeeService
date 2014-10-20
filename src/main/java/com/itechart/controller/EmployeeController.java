package com.itechart.controller;

import com.itechart.dto.EmployeeDTO;
import com.itechart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return employeeService.readEmployeeList();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveEmployeeCreate")
    public @ResponseBody void saveEmployeeCorrect(@RequestBody EmployeeDTO employeeDTO){
        employeeService.createEmployee(employeeDTO);
    }
}
