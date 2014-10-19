package com.itechart.service;

import com.itechart.model.*;

import java.util.List;
/**
 * Created by Margarita on 16.10.2014.
 */
public interface CompanyService {

    public List readCompany();
    public void createCompany(Company company);
    public void createDepartment(Department department);
    public void createAddress(Address address);
    public void createEmployee(Employee employee);
    public void createPositionInCompany(PositionInCompany positionInCompany);
    public void createProject(Project project);
    public void createUser(User user);
    public void createDepartmentEmployee(Department department, Employee employee);
    public void createCountry(Country country);
}
