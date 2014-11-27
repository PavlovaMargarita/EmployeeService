package com.itechart.service;

import com.itechart.model.dto.*;

import java.sql.Date;
import java.util.List;

public interface CompanyService {
    public List<PositionInCompanyDTO> readPositionInCompanyList();
    public List<DepartmentDTO> readDepartmentList();
    public List<AddressDTO> readAddressList(Long departmentId);
    public void updateCompany(CompanyDTO companyDTO);
    public List<CompanyDTO> readCompanyList(int pageNumber, int pageRecords);
    public long companyCount();
    public List<CompanyStatusDTO> readCompanyStatusEnum();
    public CompanyDTO readCompanyById(Long id);
    public Long createCompany(CompanyDTO companyDTO);
    public Date getDateBoundaryRefill();
    public void pay(String number) throws Exception;
    public CompanyDTO getCurrentCompany();
}
