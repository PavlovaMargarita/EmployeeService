package com.itechart.service.impl;

import com.itechart.model.*;
import com.itechart.repository.*;
import com.itechart.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Margarita on 16.10.2014.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PositionInCompanyRepository positionInCompanyRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Override
    @Transactional
    public List readCompany() {
        return  companyRepository.findAll();
    }

    @Override
    @Transactional
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    @Transactional
    public void createDepartment(Department department) {
        departmentRepository.save(department);
    }

    @Override
    @Transactional
    public void createAddress(Address address) {
        addressRepository.save(address);
    }

    @Override
    @Transactional
    public void createEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void createPositionInCompany(PositionInCompany positionInCompany) {
        positionInCompanyRepository.save(positionInCompany);
    }

    @Override
    @Transactional
    public void createProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void createCountry(Country country) {
        countryRepository.save(country);
    }

    @Override
    public User readUser(String login) {
        return userRepository.readUser(login);
    }
}
