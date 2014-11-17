package com.itechart.service.impl;

import com.itechart.dto.EmployeeDTO;
import com.itechart.dto.RoleDTO;
import com.itechart.dto.SexDTO;
import com.itechart.enumProperty.RoleEnum;
import com.itechart.enumProperty.SexEnum;
import com.itechart.model.*;
import com.itechart.repository.*;
import com.itechart.service.EmployeeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private CountryRepository countryRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PositionInCompanyRepository positionInCompanyRepository;

    //private final String LOCATION = "D:/EmployeeService/src/main/webapp/files/company/";
    private final String LOCATION = "C:/apache-tomcat-7.0.56/webapps/EmployeeService/files/company/";
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String company = authority.get(1).getAuthority();
        Long companyId = Long.parseLong(company.substring(10));
        employeeDTO.setCompanyId(companyId);
        Employee employee = employeeDTOToEmployee(employeeDTO);
        Logger.getLogger(EmployeeService.class).info("Create Employee " + employee.toString());
        return employeeRepository.save(employee).getId();

    }

    @Override
    @Transactional
    public Long updateEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOToEmployee(employeeDTO);
        employee.setId(employeeDTO.getId());
        Logger.getLogger(EmployeeService.class).info("Update Employee " + employee.toString());
        employeeRepository.save(employee);
        return employeeDTO.getId();
    }

    @Override
    public long employeeCount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String company = authority.get(1).getAuthority();
        Long companyId = Long.parseLong(company.substring(10));
        Logger.getLogger(EmployeeServiceImpl.class).info("Read employee count");
        return employeeRepository.employeeCount(companyId);
    }

    @Override
    public void loadPhoto(MultipartFile photo, Long id) {
        try {
            if(ImageIO.read(photo.getInputStream()) == null){
                System.out.println("bad image");

            } else {
                //create new photo path
                StringBuilder photoPath = new StringBuilder(LOCATION);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
                String company = authority.get(1).getAuthority();
                Long companyId = Long.parseLong(company.substring(10));
                photoPath.append(companyId);
                photoPath.append("/photoEmployee/");
                photoPath.append(id);
                photoPath.append("/");

                String fileName  = photo.getOriginalFilename();
                File pathFile = new File(photoPath.toString());
                if(!pathFile.exists()){
                    pathFile.mkdir();
                }

                pathFile = new File(photoPath.toString() + fileName);
                System.out.println(pathFile);
                //delete old image
                EmployeeDTO employeeDTO = readEmployee(id);
                try {
                    if(!employeeDTO.getPhotoURL().equals("t"))
                        Files.delete(Paths.get(photoPath.toString() + employeeDTO.getPhotoURL()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    photo.transferTo(pathFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //save url on database
                Logger.getLogger(EmployeeServiceImpl.class).info("Load file employee, id = " + employeeDTO.getId());
                employeeDTO.setPhotoURL(fileName);
                updateEmployee(employeeDTO);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("fail");
        }

    }


    @Override
    public List<SexDTO> readSexEnum() {
        List<SexDTO> sexEnum = new ArrayList();
        for(int i = 0; i < SexEnum.values().length; i++) {
            SexDTO sexDTO = new SexDTO();
            sexDTO.setSexEnum(SexEnum.values()[i]);
            if(sexDTO.getSexEnum().equals(SexEnum.FEMALE)){
                sexDTO.setRoleRussian("Женский");
            }
            if(sexDTO.getSexEnum().equals(SexEnum.MALE)){
                sexDTO.setRoleRussian("Мужской");
            }
            sexEnum.add(sexDTO);
        }
        return sexEnum;
    }

    @Override
    public EmployeeDTO readCurrentEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String company = authority.get(2).getAuthority();
        Long employeeId = Long.parseLong(company.substring(11));
        EmployeeDTO employeeDTO = readEmployee(employeeId);
        return employeeDTO;
    }

    @Override
    public List readRoleEnumForCurrentEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String company = authority.get(2).getAuthority();
        Long employeeId = Long.parseLong(company.substring(11));
        EmployeeDTO employeeDTO = readEmployee(employeeId);
        List roleList = new ArrayList();
        for(int i = 0; i < RoleEnum.values().length; i++) {
            if(RoleEnum.values()[i].equals(RoleEnum.ROLE_HRM) && (employeeDTO.getRole().equals(RoleEnum.ROLE_HRM) || employeeDTO.getRole().equals(RoleEnum.ROLE_ADMIN)))
                roleList.add(RoleEnum.values()[i]);
            if(RoleEnum.values()[i].equals(RoleEnum.ROLE_ADMIN) && (employeeDTO.getRole().equals(RoleEnum.ROLE_HRM) || employeeDTO.getRole().equals(RoleEnum.ROLE_CEO)))
                roleList.add(RoleEnum.values()[i]);
            if(RoleEnum.values()[i].equals(RoleEnum.ROLE_EMPLOYEE) && employeeDTO.getRole().equals(RoleEnum.ROLE_HRM))
                roleList.add(RoleEnum.values()[i]);

        }
        return roleList;
    }

    @Override
    public void createEmployeeCeo(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOToEmployee(employeeDTO);
        employee.setFired(false);
        PositionInCompany positionInCompany = new PositionInCompany();
        positionInCompany.setCompany(employee.getCompany());
        positionInCompany.setPosition("CEO");
        positionInCompanyRepository.save(positionInCompany);
        employee.setPositionInCompany(positionInCompany);
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeDTO readEmployeeCeoByCompanyId(Long companyId) {
        Employee employee = employeeRepository.readEmployeeCeo(companyId);
        EmployeeDTO employeeDTO = employeeToEmployeeDTO(employee);
        return employeeDTO;
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
        if(employee.getAddress() != null) {
            employeeDTO.setAddressId(employee.getAddress().getId());
        }
        employeeDTO.setDateContractEnd(employee.getDateContractEnd());
        employeeDTO.setFired(employee.getFired());
        employeeDTO.setFiredComment(employee.getFiredComment());
        employeeDTO.setPhotoURL(employee.getPhotoURL());
        employeeDTO.setLogin(employee.getLogin());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setCompanyId(employee.getCompany().getId());
        employeeDTO.setEmail(employee.getEmail());
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
        if(employeeDTO.getAddressId() != null) {
            Address address = companyRepository.readAddress(employeeDTO.getAddressId());
            employee.setAddress(address);
        }
        if(employeeDTO.getDepartmentId() != null) {
            Department department = companyRepository.readDepartment(employeeDTO.getDepartmentId());
            employee.setDepartment(department);
        }
        if(employeeDTO.getPositionInCompanyId() != null) {
            PositionInCompany positionInCompany = positionInCompanyRepository.findOne(employeeDTO.getPositionInCompanyId());
            employee.setPositionInCompany(positionInCompany);
        }

        employee.setDateContractEnd(employeeDTO.getDateContractEnd());
        employee.setFired(employeeDTO.getFired());
        employee.setFiredComment(employeeDTO.getFiredComment());
        employee.setDateFired(employeeDTO.getDateFired());
        employee.setLogin(employeeDTO.getLogin());
        employee.setPassword(employeeDTO.getPassword());
        employee.setRole(employeeDTO.getRole());
        employee.setCompany(companyRepository.findOne(employeeDTO.getCompanyId()));
        employee.setEmail(employeeDTO.getEmail());
        return employee;
    }

}
