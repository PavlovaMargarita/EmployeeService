package com.itechart.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Margarita on 17.10.2014.
 */
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String projectName;

    @ManyToMany(mappedBy = "projectList")
    private List<Employee> employeeList;

    public Project(){
        employeeList = new ArrayList<Employee>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }
}
