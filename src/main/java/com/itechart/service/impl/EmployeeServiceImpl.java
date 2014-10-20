package com.itechart.service.impl;

import com.itechart.dto.DepartmentDTO;
import com.itechart.dto.EmployeeDTO;
import com.itechart.model.Department;
import com.itechart.model.Employee;
import com.itechart.repository.EmployeeRepository;
import com.itechart.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Margarita on 20.10.2014.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public List <EmployeeDTO> readEmployeeList() {
        List<Employee> employeeList = employeeRepository.findAll();
        List <EmployeeDTO> employeeDTOList = new ArrayList();
        for(Employee employee: employeeList){
            employeeDTOList.add(employeeToEmployeeDTO(employee));
        }
        return employeeDTOList;
    }

    @Override
    public EmployeeDTO readEmployee(Long id) {
        Employee employee = employeeRepository.findOne(id);
        EmployeeDTO employeeDTO = employeeToEmployeeDTO(employee);
        return employeeDTO;
    }

    private EmployeeDTO employeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setF_name(employee.getS_name());
        employeeDTO.setS_name(employee.getS_name());
        Department department = employee.getDepartment();
        DepartmentDTO departmentDTO = departmentToDepartmentDTO(department);
        employeeDTO.setDepartmentDTO(departmentDTO);
        return employeeDTO;
    }

    private DepartmentDTO departmentToDepartmentDTO(Department department){
        DepartmentDTO departmentDTO = new DepartmentDTO();
        if(department != null) {
            departmentDTO.setId(department.getId());
            departmentDTO.setDepartment(department.getDepartmentName());
        }
        return departmentDTO;
    }
}
