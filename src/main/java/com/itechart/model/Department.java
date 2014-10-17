package com.itechart.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Margarita on 16.10.2014.
 */
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, name = "department_name")
    private String departmentName;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = true)
    private Address address;

    @OneToMany(mappedBy = "mainDepartment", cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<Department> subDepartment;

    @ManyToOne
    @JoinColumn(name = "main_department_id", nullable = true)
    private Department mainDepartment;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employeeList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "head_of_department_id", nullable = false)
    private Employee headEmployee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "deputy_head_of_department_id", nullable = false)
    private Employee deputyHeadEmployee;

    public Department(){
        subDepartment = new ArrayList<Department>();
        employeeList = new ArrayList<Employee>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Department> getSubDepartment() {
        return subDepartment;
    }

    public void setSubDepartment(List<Department> subDepartment) {
        this.subDepartment = subDepartment;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public Employee getHeadEmployee() {
        return headEmployee;
    }

    public void setHeadEmployee(Employee headEmployee) {
        this.headEmployee = headEmployee;
    }

    public Employee getDeputyHeadEmployee() {
        return deputyHeadEmployee;
    }

    public void setDeputyHeadEmployee(Employee deputyHeadEmployee) {
        this.deputyHeadEmployee = deputyHeadEmployee;
    }

    public Department getMainDepartment() {
        return mainDepartment;
    }

    public void setMainDepartment(Department mainDepartment) {
        this.mainDepartment = mainDepartment;
    }
}
