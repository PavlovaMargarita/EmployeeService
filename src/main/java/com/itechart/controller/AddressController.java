package com.itechart.controller;

import com.itechart.dto.AddressDTO;
import com.itechart.service.DepartmentService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Margarita on 20.10.2014.
 */

@Controller
@RequestMapping("/EmployeeService/address")
public class AddressController {
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(method = RequestMethod.GET, value = "/addressList")
    @ResponseBody
    public List<AddressDTO> addressList(@RequestParam("departmentId") Long id ){
        Logger.getLogger(AddressController.class).info("Request: /EmployeeService/address/addressList, parameter id = " + id);
        return departmentService.readAddressList(id);
    }
}
