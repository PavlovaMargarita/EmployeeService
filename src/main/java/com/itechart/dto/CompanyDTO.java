package com.itechart.dto;

import com.itechart.enumProperty.CompanyStatusEnum;

import java.sql.Date;

/**
 * Created by Margarita on 29.10.2014.
 *
 */
public class CompanyDTO {

    private Long id;
    private String companyName;
    private Integer accountSum;
    private Date dateBoundaryRefill;
    private Boolean canLogin;
    private CompanyStatusEnum companyStatus;
    private Integer addSum;
    public CompanyDTO(){
        addSum = 0;
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

    public Date getDateBoundaryRefill() {
        return dateBoundaryRefill;
    }

    public void setDateBoundaryRefill(Date dateBoundaryRefill) {
        this.dateBoundaryRefill = dateBoundaryRefill;
    }

    public Integer getAccountSum() {

        return accountSum;
    }

    public void setAccountSum(Integer accountSum) {
        this.accountSum = accountSum;
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

    public Integer getAddSum() {
        return addSum;
    }

    public void setAddSum(Integer addSum) {
        this.addSum = addSum;
    }
}
