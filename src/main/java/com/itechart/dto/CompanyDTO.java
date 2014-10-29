package com.itechart.dto;

import java.sql.Date;

/**
 * Created by Margarita on 29.10.2014.
 *
 */
public class CompanyDTO {

    private Long id;
    private String companyName;
    private Integer accountSum;
    private Date dateLastRefill;
    private Boolean canLogin;
    public CompanyDTO(){

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

    public Date getDateLastRefill() {
        return dateLastRefill;
    }

    public void setDateLastRefill(Date dateLastRefill) {
        this.dateLastRefill = dateLastRefill;
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
}
