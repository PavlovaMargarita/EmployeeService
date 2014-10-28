package com.itechart.service.impl;

import com.itechart.dto.EmployeeDTO;
import com.itechart.model.*;
import com.itechart.repository.*;
import com.itechart.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
    public List <EmployeeDTO> readEmployeeList(int pageNumber, int pageRecords) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String company = authority.get(1).getAuthority();
        Long companyId = Long.parseLong(company.substring(10));
        Pageable topTen = new PageRequest(pageNumber, pageRecords);
        Logger.getLogger(EmployeeServiceImpl.class).info("Read Employee List, page = "+ pageNumber+", count=" + pageRecords);
        List<Employee> employeeList = employeeRepository.readEmployeeList(companyId, topTen);
        List <EmployeeDTO> employeeDTOList = new ArrayList(employeeList.size());
        for(Employee employee: employeeList){
            employeeDTOList.add(employeeToEmployeeDTO(employee));
        }
        return employeeDTOList;
    }

    @Override
    public EmployeeDTO readEmployee(Long id) {
        Logger.getLogger(EmployeeService.class).info(String.format("Read Employee by id %s",id));
        Employee employee = employeeRepository.findOne(id);
        Logger.getLogger(EmployeeService.class).info("Return Employee" + employee.toString());
        EmployeeDTO employeeDTO = employeeToEmployeeDTO(employee);
        return employeeDTO;
    }

    @Override
    public Long createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOToEmployee(employeeDTO);
        Logger.getLogger(EmployeeService.class).info("Create Employee " + employee.toString());
        return employeeRepository.save(employee).getId();

    }

    @Override
    @Transactional
    public void updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOToEmployee(employeeDTO);
        employee.setId(employeeDTO.getId());
        Logger.getLogger(EmployeeService.class).info("Update Employee " + employee.toString());
        employeeRepository.save(employee);
    }

    @Override
    public long employeeCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String company = authority.get(1).getAuthority();
        Long companyId = Long.parseLong(company.substring(10));
        return employeeRepository.employeeCount(companyId);
    }

    @Override
    public void loadPhoto(MultipartFile photo, Long id) {
        System.out.println("ok");
        String classPath = this.getClass().getClassLoader().getResource("").getPath();
        String separator = "/";
        StringBuilder photoPath = new StringBuilder();
        StringTokenizer stringTokenizer = new StringTokenizer(classPath, separator);
        while (stringTokenizer.hasMoreTokens()){
            String token = (String)stringTokenizer.nextElement();
            if(!token.equals("target")){
                photoPath.append(token);
                photoPath.append(separator);
            }else break;
        }
        photoPath.append("src");
        photoPath.append("/");
        photoPath.append("main");
        photoPath.append(separator);
        photoPath.append("webapp");
        photoPath.append(separator);
        photoPath.append("photoEmployee");
        photoPath.append(separator);
        photoPath.append(id);
        photoPath.append(separator);
        String fileName  = photo.getOriginalFilename();
        File pathFile = new File(photoPath.toString());
        if(!pathFile.exists()){
            pathFile.mkdir();
        }
        pathFile = new File(photoPath.toString() + fileName);
        try {
            photo.transferTo(pathFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //save url on database
        EmployeeDTO employeeDTO = readEmployee(id);
        employeeDTO.setPhotoURL(fileName);
        updateEmployee(employeeDTO);
    }

    private EmployeeDTO employeeToEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setF_name(employee.getF_name());
        employeeDTO.setS_name(employee.getS_name());
        if(employee.getDepartment() != null ) {
            employeeDTO.setDepartmentId(employee.getDepartment().getId());
            employeeDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
        }
        employeeDTO.setDateOfBirth(employee.getDateOfBirth());
        employeeDTO.setSex(employee.getSex());
        employeeDTO.setCountryId(employee.getCountry().getId());
        employeeDTO.setCity(employee.getCity());
        employeeDTO.setStreet(employee.getStreet());
        employeeDTO.setHouse(employee.getHouse());
        employeeDTO.setFlat(employee.getFlat());
        employeeDTO.setPositionInCompanyId(employee.getPositionInCompany().getId());
        employeeDTO.setAddressId(employee.getAddress().getId());
        employeeDTO.setDateContractEnd(employee.getDateContractEnd());
        employeeDTO.setFired(employee.getFired());
        employeeDTO.setFiredComment(employee.getFiredComment());
        employeeDTO.setPhotoURL(employee.getPhotoURL());
        return employeeDTO;
    }

    private Employee employeeDTOToEmployee(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setF_name(employeeDTO.getF_name());
        employee.setS_name(employeeDTO.getS_name());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setSex(employeeDTO.getSex());
        Country country = countryRepository.findOne(employeeDTO.getCountryId());
        employee.setCountry(country);
        employee.setCity(employeeDTO.getCity());
        employee.setStreet(employeeDTO.getStreet());
        employee.setHouse(employeeDTO.getHouse());
        employee.setFlat(employeeDTO.getFlat());
        employee.setPhotoURL(employeeDTO.getPhotoURL());
        Address address = addressRepository.findOne(employeeDTO.getAddressId());
        employee.setAddress(address);
        Department department = departmentRepository.findOne(employeeDTO.getDepartmentId());
        employee.setDepartment(department);
        PositionInCompany positionInCompany = positionInCompanyRepository.findOne(employeeDTO.getPositionInCompanyId());
        employee.setPositionInCompany(positionInCompany);
        employee.setDateContractEnd(employeeDTO.getDateContractEnd());
        employee.setFired(employeeDTO.getFired());
        employee.setFiredComment(employeeDTO.getFiredComment());
        employee.setDateFired(employeeDTO.getDateFired());
        return employee;
    }

}
