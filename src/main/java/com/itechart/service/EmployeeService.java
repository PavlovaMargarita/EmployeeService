package com.itechart.service;

import com.itechart.dto.EmployeeDTO;
import com.itechart.dto.RoleDTO;
import com.itechart.dto.SexDTO;
import com.itechart.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Margarita on 20.10.2014.
 */
public interface EmployeeService {
    public List<EmployeeDTO> readEmployeeList(int pageNumber, int pageRecords);
    public EmployeeDTO readEmployee(Long id);
    public Long createEmployee(EmployeeDTO employeeDTO);
    public Long updateEmployee(EmployeeDTO employeeDTO);
    public long employeeCount();
    //load photo
    public void loadPhoto(MultipartFile photo, Long id);
    public List<SexDTO> readSexEnum();
    public EmployeeDTO readCurrentEmployee();
    public List<RoleDTO> readRoleEnum();
}
