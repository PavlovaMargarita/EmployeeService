package com.itechart.service;

import com.itechart.dto.EmployeeDTO;

import java.util.*;

/**
 * Created by Margarita on 20.10.2014.
 */
public interface EmployeeService {
    public List readEmployeeList();
    public EmployeeDTO readEmployee(Long id);
}
