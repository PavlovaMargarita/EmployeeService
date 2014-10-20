package com.itechart.dto;

/**
 * Created by Margarita on 20.10.2014.
 */
public class DepartmentDTO {
    private Long id;
    private String department;
    public DepartmentDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
