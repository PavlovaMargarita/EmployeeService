package com.itechart;

import com.itechart.enumProperty.SexEnum;
import com.itechart.model.*;
import com.itechart.service.CompanyService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.*;
import java.sql.Date;

/**
 * Created by Margarita on 16.10.2014.
 */
public class Main {
    public static void main(String[] args) {

        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("spring.config.xml");
        CompanyService companyService =
                (CompanyService) ctx.getBean("companyServiceImpl");
        Company company = new Company();
        company.setCompanyName("test2");
        company.setCanLogin(true);

        Company company1 = new Company();
        company1.setCompanyName("test3");
        company1.setCanLogin(true);

        PositionInCompany positionInCompany = new PositionInCompany();
        positionInCompany.setPosition("HRM");
        positionInCompany.setCompany(company);

        Department department = new Department();
        department.setDepartmentName("test");
        department.setCompany(company);

        Country country = new Country();
        country.setCountry("test");
        companyService.createCountry(country);

        Address address = new Address();
        address.setStreet("qwe");
        address.setCity("qwe");
        address.setCountry(country);
        address.setFlat("1");
        address.setHouse(1);
        List addressList = new ArrayList();
        addressList.add(address);

        department.setAddressList(addressList);

        companyService.createAddress(address);
        companyService.createCompany(company);
        companyService.createCompany(company1);
        companyService.createPositionInCompany(positionInCompany);

        Project project = new Project();
        project.setProjectName("1");
        Project project1 = new Project();
        project1.setProjectName("1");
        companyService.createProject(project);
        companyService.createProject(project1);

        Employee employee = new Employee();
        employee.setHouse(1);
        employee.setFlat("1");
        employee.setCountry(country);
        employee.setCity("q");
        employee.setF_name("q");
        employee.setS_name("q");
        employee.setSex(SexEnum.FEMALE);
        employee.setStreet("q");
        employee.setDateOfBirth(new Date(10, 10, 2010));
        employee.setPhotoURL("qwe");
        employee.setAddress(address);
//        employee.setDepartmentList(department);
        employee.setPositionInCompany(positionInCompany);
        List companies = new ArrayList();
        companies.add(company);
        companies.add(company1);
        employee.setCompanyList(companies);
        List projects = new ArrayList();
        projects.add(project);
        projects.add(project1);
        employee.setProjectList(projects);
        companyService.createEmployee(employee);

        department.setHeadEmployee(employee);
        department.setDeputyHeadEmployee(employee);
        companyService.createDepartment(department);

//        companyService.createDepartmentEmployee(department, employee);
        User user = new User();
        user.setLogin("qwe");
        user.setPassword("qwe");
        user.setEmployee(employee);
        companyService.createUser(user);

    }
}
