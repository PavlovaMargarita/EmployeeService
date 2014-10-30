package com.itechart.dto;

import com.itechart.enumProperty.CompanyStatusEnum;

/**
 * Created by Margarita on 30.10.2014.
 */
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
