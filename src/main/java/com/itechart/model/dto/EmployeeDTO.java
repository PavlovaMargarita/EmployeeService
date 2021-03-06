package com.itechart.model.dto;

import com.itechart.enumProperty.RoleEnum;
import com.itechart.enumProperty.SexEnum;

import java.sql.Date;

public class EmployeeDTO {

    private Long id;
    private String first_name;
    private String last_name;
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
    private Date dateFired;
    private String photoURL;
    private String login;
    private String password;
    private RoleEnum role;
    private Long companyId;
    private String email;

    public EmployeeDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    public Date getDateFired() {
        return dateFired;
    }

    public void setDateFired(Date dateFired) {
        this.dateFired = dateFired;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
