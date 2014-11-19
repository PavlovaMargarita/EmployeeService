package com.itechart.model;

import com.itechart.enumProperty.RoleEnum;
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

    @Column(nullable = false, name = "first_name")
    private String firstName;

    @Column(nullable = false, name = "last_name")
    private String lastName;

    @Column(nullable = false, name = "date_of_birth")
    private Date dateOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SexEnum sex;

    @ManyToOne
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

    @Column(nullable = true, name="photo_url")
    private String photoURL;

    @Column(nullable = false)
    private String email;

    @ManyToOne
    @JoinColumn(name = "office_address_id", nullable = true)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

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


    @Column(nullable = true, name="date_contract_end")
    private Date dateContractEnd;

    @Column(nullable = false)
    private Boolean fired;

    @Column(nullable = true, name = "fired_comment")
    private String firedComment;

    @Column(nullable = true)
    private Date dateFired;

    @Column(nullable = false)
    private String login;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String s_name) {
        this.lastName = s_name;
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

    public Date getDateFired() {
        return dateFired;
    }

    public void setDateFired(Date dateFired) {
        this.dateFired = dateFired;
    }
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", f_name='" + firstName + '\'' +
                ", s_name='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", sex=" + sex +
                ", country=" + country.getCountry() +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house=" + house +
                ", flat='" + flat + '\'' +
                ", photoURL='" + photoURL + '\'' +
//                ", departmentAddress: country='" + address.getCountry().getCountry() + ", city='"+ address.getCity() +
//                ", street='" + address.getStreet() + ", house='" + address.getHouse() + ", flat='" + address.getFlat() +'\'' +
//                ", department=" + department.getDepartmentName() +
                ", positionInCompany=" + positionInCompany.getPosition() +
                ", dateContractEnd=" + dateContractEnd +
                ", fired=" + fired +
                ", firedComment='" + firedComment + '\'' +
                '}';
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
