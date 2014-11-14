package com.itechart.model;

import com.itechart.enumProperty.CompanyStatusEnum;

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

    @Column(nullable = false, name = "date_boundary_refill")
    private Date dateBoundaryRefill;

    @Column(nullable = false, name = "status_functioning")
    @Enumerated(EnumType.STRING)
    private CompanyStatusEnum companyStatus;

    @Column(nullable = false, name = "program_cost")
    private Integer programCost;

    @Column(nullable = false, name = "company_plan")
    private String companyPlan;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Department> departmentList;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<PositionInCompany> positionInCompanyList;

    @ManyToMany(mappedBy = "companyList")
    private List<Employee> formerEmployeeList;

    @OneToMany(mappedBy = "company")
    private List<Employee> employeeList;

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

    public Date getDateBoundaryRefill() {
        return dateBoundaryRefill;
    }

    public void setDateBoundaryRefill(Date dateBoundaryRefill) {
        this.dateBoundaryRefill = dateBoundaryRefill;
    }

    public CompanyStatusEnum getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(CompanyStatusEnum companyStatus) {
        this.companyStatus = companyStatus;
    }
    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public Integer getProgramCost() {
        return programCost;
    }

    public void setProgramCost(Integer programCost) {
        this.programCost = programCost;
    }

    public String getCompanyPlan() {
        return companyPlan;
    }

    public void setCompanyPlan(String companyPlan) {
        this.companyPlan = companyPlan;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", canLogin=" + canLogin +
                ", dateBoundaryRefill=" + dateBoundaryRefill +
                ", companyStatus=" + companyStatus +
                '}';
    }
}
