package com.itechart.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import com.itechart.model.Employee;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "INT unsigned")
    private Long id;

    @Column(nullable = false, name = "department_name")
    private String departmentName;

    @ManyToMany
    @JoinTable(name = "department_address", joinColumns = {
            @JoinColumn(name = "department_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "address_id",
                    nullable = false, updatable = false) })
    private List<Address> addressList;

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

    @OneToOne
    @JoinColumn(name = "head_of_department_id", nullable = false)
    private Employee headEmployee;

    @OneToOne
    @JoinColumn(name = "deputy_head_of_department_id", nullable = false)
    private Employee deputyHeadEmployee;

    public Department(){
        subDepartment = new ArrayList<Department>();
        employeeList = new ArrayList<Employee>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
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
