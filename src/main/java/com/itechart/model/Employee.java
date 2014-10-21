package com.itechart.model;

import com.itechart.enumProperty.SexEnum;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Margarita on 17.10.2014.
 */
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "INT unsigned")
    private Long id;

    @Column(nullable = false)
    private String f_name;

    @Column(nullable = false)
    private String s_name;

    @Column(nullable = false, name = "date_of_birth")
    private Date dateOfBirth;

    @Column(nullable = false)
    private SexEnum sex;

    @OneToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private Integer house;

    @Column(nullable = false)
    private String flat;

    @Column(nullable = false, name="photo_url")
    private String photoURL;

    @ManyToOne
    @JoinColumn(name = "office_address_id", nullable = true)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "position_in_company_id", nullable = false)
    private PositionInCompany positionInCompany;

    @ManyToMany
    @JoinTable(name = "employee_company", joinColumns = {
            @JoinColumn(name = "employee_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "company_id",
                    nullable = false, updatable = false) })
    private List<Company> companyList;

    @ManyToMany
    @JoinTable(name = "employee_project", joinColumns = {
            @JoinColumn(name = "employee_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "project_id",
                    nullable = false, updatable = false) })
    private List<Project> projectList;


    @Column(nullable = false, name="date_contract_end")
    private Date dateContractEnd;

    @Column(nullable = false)
    private Boolean fired;

    @Column(nullable = true, name = "fired_comment")
    private String firedComment;

    public Employee(){
        companyList = new ArrayList<Company>();
        projectList = new ArrayList<Project>();
    }

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public PositionInCompany getPositionInCompany() {
        return positionInCompany;
    }

    public void setPositionInCompany(PositionInCompany positionInCompany) {
        this.positionInCompany = positionInCompany;
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<Company> companyList) {
        this.companyList = companyList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
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
