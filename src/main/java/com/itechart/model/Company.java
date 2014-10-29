package com.itechart.model;

import javax.persistence.*;
import java.util.*;
import java.sql.Date;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "INT unsigned")
    private Long id;

    @Column(nullable = false, name = "company_name")
    private String companyName;

    @Column(nullable = false, name = "can_login")
    private Boolean canLogin;

    @Column(nullable = false, name = "account_sum")
    private Integer accountSum;

    @Column(nullable = false, name = "date_last_refill")
    private Date dateLastRefill;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getAccountSum() {
        return accountSum;
    }

    public void setAccountSum(Integer accountSum) {
        this.accountSum = accountSum;
    }

    public java.sql.Date getDateLastRefill() {
        return dateLastRefill;
    }

    public void setDateLastRefill(java.sql.Date dateLastRefill) {
        this.dateLastRefill = dateLastRefill;
    }
}
