package com.itechart.service.impl;

import com.itechart.dto.EmployeeDTO;
import com.itechart.enumProperty.SexEnum;
import com.itechart.model.*;
import com.itechart.repository.*;
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

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PositionInCompanyRepository positionInCompanyRepository;

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

    @Override
    public void createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOToEmployee(employeeDTO);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOToEmployee(employeeDTO);
        employee.setId(employeeDTO.getId());
        employeeRepository.updateEmployee(employee.getId(), employee.getF_name());
    }

    private EmployeeDTO employeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setF_name(employee.getS_name());
        employeeDTO.setS_name(employee.getS_name());
        if(employee.getDepartment() != null ) {
            employeeDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
        }
        employeeDTO.setDateOfBirth(employee.getDateOfBirth());
        employeeDTO.setCountryId(employee.getCountry().getId());
        employeeDTO.setCity(employee.getCity());
        employeeDTO.setStreet(employee.getStreet());
        employeeDTO.setHouse(employee.getHouse());
        employeeDTO.setFlat(employee.getFlat());
        employeeDTO.setPositionInCompanyId(employee.getPositionInCompany().getId());
        employeeDTO.setAddressId(employee.getAddress().getId());
        return employeeDTO;
    }

    private Employee employeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setF_name(employeeDTO.getF_name());
        employee.setS_name(employeeDTO.getS_name());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setSex(SexEnum.FEMALE);
        Country country = countryRepository.findOne(employeeDTO.getCountryId());
        employee.setCountry(country);
        employee.setCity(employeeDTO.getCity());
        employee.setStreet(employeeDTO.getStreet());
        employee.setHouse(employeeDTO.getHouse());
        employee.setFlat(employeeDTO.getFlat());
        employee.setPhotoURL("qwe");
        Address address = addressRepository.findOne(employeeDTO.getAddressId());
        employee.setAddress(address);
        Department department = departmentRepository.findOne(employeeDTO.getDepartmentId());
        employee.setDepartment(department);
        PositionInCompany positionInCompany = positionInCompanyRepository.findOne(employeeDTO.getPositionInCompanyId());
        employee.setPositionInCompany(positionInCompany);
        return employee;
    }

}
