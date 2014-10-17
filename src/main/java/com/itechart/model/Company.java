package com.itechart.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, name = "company_name")
    private String companyName;

    @Column(nullable = false, name = "can_login")
    private Boolean canLogin;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Department> departmentList;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<PositionInCompany> positionInCompanyList;

    @ManyToMany(mappedBy = "companyList")
    private List<Employee> formerEmployeeList;

    public Company(){
        departmentList = new ArrayList<Department>();
        positionInCompanyList = new ArrayList<PositionInCompany>();
        formerEmployeeList = new ArrayList<Employee>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Boolean getCanLogin() {
        return canLogin;
    }

    public void setCanLogin(Boolean canLogin) {
        this.canLogin = canLogin;
    }

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public List<PositionInCompany> getPositionInCompanyList() {
        return positionInCompanyList;
    }

    public void setPositionInCompanyList(List<PositionInCompany> positionInCompanyList) {
        this.positionInCompanyList = positionInCompanyList;
    }

    public List<Employee> getFormerEmployeeList() {
        return formerEmployeeList;
    }

    public void setFormerEmployeeList(List<Employee> formerEmployeeList) {
        this.formerEmployeeList = formerEmployeeList;
    }
}
