package com.itechart.dto;

import com.itechart.enumProperty.SexEnum;

import java.sql.Date;

/**
 * Created by Margarita on 20.10.2014.
 */
public class EmployeeDTO {

    private Long id;
    private String f_name;
    private String s_name;
    private Date dateOfBirth;
    private SexEnum sex;
    private Long countryId;
    private String city;
    private String street;
    private Integer house;
    private String flat;
    private Long addressId;
    private Long departmentId;
    private String departmentName;
    private Long positionInCompanyId;
    private Date dateContractEnd;
    private Boolean fired;
    private String firedComment;

    public EmployeeDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getHouse() {
        return house;
    }

    public void setHouse(Integer house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getPositionInCompanyId() {
        return positionInCompanyId;
    }

    public void setPositionInCompanyId(Long positionInCompanyId) {
        this.positionInCompanyId = positionInCompanyId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getDateContractEnd() {
        return dateContractEnd;
    }

    public void setDateContractEnd(Date dateContractEnd) {
        this.dateContractEnd = dateContractEnd;
    }

    public Boolean getFired() {
        return fired;
    }

    public void setFired(Boolean fired) {
        this.fired = fired;
    }

    public String getFiredComment() {
        return firedComment;
    }

    public void setFiredComment(String firedComment) {
        this.firedComment = firedComment;
    }
}