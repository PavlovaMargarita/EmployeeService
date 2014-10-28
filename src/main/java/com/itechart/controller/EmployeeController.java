package com.itechart.controller;

import com.itechart.dto.EmployeeDTO;
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
@RequestMapping("/EmployeeService/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.GET, value = "/employeeList")
    @ResponseBody
    public List<EmployeeDTO> employeeList(@RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords){
        Logger.getLogger(AddressController.class).info("Request /EmployeeService/employee/employeeList ");
        int firstRecordNumber = firstRecordNumber(currentPage, pageRecords);
        return employeeService.readEmployeeList(currentPage - 1, pageRecords);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/employeeCount")
    @ResponseBody
    public long employeeCount(){
        return employeeService.employeeCount();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveEmployeeCreate")
    public @ResponseBody Long saveEmployeeCorrect(@RequestBody EmployeeDTO employeeDTO){
        Logger.getLogger(AddressController.class).info("Request /EmployeeService/employee/saveEmployeeCreate ");
        Long id = employeeService.createEmployee(employeeDTO);
        return id;
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

//    @RequestMapping(method = RequestMethod.POST, value = "/upload")
//    public @ResponseBody void upload(HttpServletRequest request, HttpServletResponse response){
//        System.out.println("ok");
//    }

    @ResponseBody
    @RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
    public void postFile(@RequestParam(value="photo", required=false) MultipartFile file,
                           @RequestParam(value="idEmployee") String data) throws Exception {

        System.out.println("data = " + data);
        Long id = Long.parseLong(data);
        employeeService.loadPhoto(file, id);

    }

    private int firstRecordNumber(int currentPage, int count){
        int firstRecordNumber = (currentPage - 1) * count;
        return firstRecordNumber >= 0 ? firstRecordNumber : 0;
    }
}
