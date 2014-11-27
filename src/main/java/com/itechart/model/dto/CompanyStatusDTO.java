package com.itechart.model.dto;

import com.itechart.enumProperty.CompanyStatusEnum;

public class CompanyStatusDTO {
    private CompanyStatusEnum companyStatusEnum;
    private String companyStatusEnumRussian;
    public CompanyStatusDTO(){

    }

    public CompanyStatusEnum getCompanyStatusEnum() {
        return companyStatusEnum;
    }

    public void setCompanyStatusEnum(CompanyStatusEnum companyStatusEnum) {
        this.companyStatusEnum = companyStatusEnum;
    }

    public String getCompanyStatusEnumRussian() {
        return companyStatusEnumRussian;
    }

    public void setCompanyStatusEnumRussian(String companyStatusEnumRussian) {
        this.companyStatusEnumRussian = companyStatusEnumRussian;
    }
}
