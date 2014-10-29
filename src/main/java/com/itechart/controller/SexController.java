package com.itechart.controller;

import com.itechart.dto.SexDTO;
import com.itechart.service.SexService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Margarita on 21.10.2014.
 */
@Controller
@RequestMapping("/EmployeeService/sex")
public class SexController {
    @Autowired
    private SexService sexService;

    @RequestMapping(method = RequestMethod.GET, value = "/sexList")
    @ResponseBody
    public List<SexDTO> addressList(){
        Logger.getLogger(AddressController.class).info("Request: /EmployeeService/sex/sexList");
        return sexService.readSexEnum();
    }
}
