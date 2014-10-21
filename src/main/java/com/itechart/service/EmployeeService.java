package com.itechart.service;

import com.itechart.dto.EmployeeDTO;

import java.util.List;

/**
 * Created by Margarita on 20.10.2014.
 */
public interface EmployeeService {
    public List<EmployeeDTO> readEmployeeList();
    public EmployeeDTO readEmployee(Long id);
    public void createEmployee(EmployeeDTO employeeDTO);
    public void updateEmployee(EmployeeDTO employeeDTO);
}
