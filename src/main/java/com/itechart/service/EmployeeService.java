package com.itechart.service;

import com.itechart.model.dto.EmployeeDTO;
import com.itechart.model.dto.SearchResult;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {
    public List<EmployeeDTO> readEmployeeList(int pageNumber, int pageRecords);
    public EmployeeDTO readEmployee(Long id);
    public Long createEmployee(EmployeeDTO employeeDTO) throws IOException, SolrServerException;
    public Long updateEmployee(EmployeeDTO employeeDTO) throws IOException, SolrServerException;
    public long employeeCount();
    //load photo
    public void loadPhoto(MultipartFile photo, Long id) throws Exception;
    public EmployeeDTO readCurrentEmployee();
    public List readRoleEnumForCurrentEmployee();
    public List readRoleEnum();
    public void createEmployeeCeo(EmployeeDTO employeeDTO) throws IOException, SolrServerException;

    public EmployeeDTO readEmployeeCeoByCompanyId(Long companyId);

    public SearchResult search(String searchValue, int page, int pageRecords) throws SolrServerException;

}
