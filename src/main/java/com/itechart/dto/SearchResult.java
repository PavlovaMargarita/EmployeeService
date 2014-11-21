package com.itechart.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marharyta.pavlova on 21.11.2014.
 */
public class SearchResult {
    private List<EmployeeDTO> employeeList = new ArrayList<>();
    private long totalSearchCount;

    public List<EmployeeDTO> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<EmployeeDTO> employeeList) {
        this.employeeList = employeeList;
    }

    public long getTotalSearchCount() {
        return totalSearchCount;
    }

    public void setTotalSearchCount(long totalSearchCount) {
        this.totalSearchCount = totalSearchCount;
    }
}
