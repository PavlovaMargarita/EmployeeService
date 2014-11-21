package com.itechart.service;

import com.itechart.dto.EmployeeDTO;
import com.itechart.dto.SearchResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {
    public List<EmployeeDTO> readEmployeeList(int pageNumber, int pageRecords);
    public EmployeeDTO readEmployee(Long id);
    public Long createEmployee(EmployeeDTO employeeDTO);
    public Long updateEmployee(EmployeeDTO employeeDTO);
    public long employeeCount();
    //load photo
    public void loadPhoto(MultipartFile photo, Long id);
    public EmployeeDTO readCurrentEmployee();
    public List readRoleEnumForCurrentEmployee();
    public List readRoleEnum();
    public void createEmployeeCeo(EmployeeDTO employeeDTO);
    public EmployeeDTO readEmployeeCeoByCompanyId(Long companyId);
    public SearchResult search(String searchValue, int page, int pageRecords);

}
