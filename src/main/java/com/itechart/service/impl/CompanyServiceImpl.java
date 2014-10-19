package com.itechart.service.impl;

import com.itechart.dao.*;
import com.itechart.model.*;
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
    private CompanyDAO companyDAO;

    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    private AddressDAO addressDAO;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private PositionInCompanyDAO positionInCompanyDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CountryDAO countryDAO;

    @Override
    @Transactional
    public List readCompany() {
        return  companyDAO.readCompany();
    }

    @Override
    @Transactional
    public void createCompany(Company company) {
        companyDAO.createCompany(company);
    }

    @Override
    @Transactional
    public void createDepartment(Department department) {
        departmentDAO.createDepartment(department);
    }

    @Override
    @Transactional
    public void createAddress(Address address) {
        addressDAO.createAddress(address);
    }

    @Override
    @Transactional
    public void createEmployee(Employee employee) {
        employeeDAO.createEmployee(employee);
    }

    @Override
    @Transactional
    public void createPositionInCompany(PositionInCompany positionInCompany) {
        positionInCompanyDAO.createPosition(positionInCompany);
    }

    @Override
    @Transactional
    public void createProject(Project project) {
        projectDAO.createProject(project);
    }

    @Override
    @Transactional
    public void createUser(User user) {
        userDAO.createUser(user);
    }

    @Override
    @Transactional
    public void createDepartmentEmployee(Department department, Employee employee) {
        departmentDAO.createDepartment(department);
        employeeDAO.createEmployee(employee);
    }

    @Override
    @Transactional
    public void createCountry(Country country) {
        countryDAO.createCountry(country);
    }
}
