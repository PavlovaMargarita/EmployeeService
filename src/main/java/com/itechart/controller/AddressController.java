package com.itechart.controller;

import com.itechart.dto.AddressDTO;
import com.itechart.service.AddressService;
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
@RequestMapping("/EmployeeService/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @RequestMapping(method = RequestMethod.GET, value = "/addressList")
    @ResponseBody
    public List<AddressDTO> addressList(){
        return addressService.readAddressList();
    }
}
