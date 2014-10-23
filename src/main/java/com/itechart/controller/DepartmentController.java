package com.itechart.controller;

import com.itechart.dto.DepartmentDTO;
import com.itechart.service.DepartmentService;
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
@RequestMapping("/EmployeeService/department")
public class DepartmentController {


    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.GET, value = "/departmentList")
    @ResponseBody
    public List<DepartmentDTO> departmentList(){
        Logger.getLogger(AddressController.class).info("Request /EmployeeService/department/departmentList ");
        return departmentService.readDepartmentList();
    }


}
