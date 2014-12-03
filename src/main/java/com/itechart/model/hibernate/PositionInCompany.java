package com.itechart.model.hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "position_in_company")
public class PositionInCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "INT unsigned")
    private Long id;

    @Column(nullable = false)
    private String position;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = true)
    private Company company;

    @OneToMany(mappedBy = "positionInCompany", cascade = CascadeType.ALL)
    private List<Employee> employeeList;

    public PositionInCompany(){
        employeeList = new ArrayList<Employee>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }


}
