package com.itechart.model.dto;

import com.itechart.enumProperty.CompanyStatusEnum;

import java.sql.Date;

public class CompanyDTO {

    private Long id;
    private String companyName;
    private Date dateBoundaryRefill;
    private Boolean canLogin;
    private CompanyStatusEnum companyStatus;
    private Integer programCost;
    private String companyPlan;
    private Integer employeeCount;
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

    public Date getDateBoundaryRefill() {
        return dateBoundaryRefill;
    }

    public void setDateBoundaryRefill(Date dateBoundaryRefill) {
        this.dateBoundaryRefill = dateBoundaryRefill;
    }

    public Boolean getCanLogin() {
        return canLogin;
    }

    public void setCanLogin(Boolean canLogin) {
        this.canLogin = canLogin;
    }

    public CompanyStatusEnum getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyStatus(CompanyStatusEnum companyStatus) {
        this.companyStatus = companyStatus;
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

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }
}
