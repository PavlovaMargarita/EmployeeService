package com.itechart.service;

import com.itechart.dto.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by Margarita on 16.10.2014.
 */
public interface CompanyService {
    public List<PositionInCompanyDTO> readPositionInCompanyList();
    public List<DepartmentDTO> readDepartmentList();
    public List<AddressDTO> readAddressList(Long departmentId);
    public List<CompanyDTO> readCompanyList();
    public void updateCompanyEveryDay(CompanyDTO companyDTO);
    public void updateCompany(CompanyDTO companyDTO);
    public List<CompanyDTO> readCompanyList(int pageNumber, int pageRecords);
    public long companyCount();
    public List<CompanyStatusDTO> readCompanyStatusEnum();
    public CompanyDTO readCompany(Long id);
    public void createCompany(CompanyDTO companyDTO);
    public Date getDateBoundaryRefill();
    public Long getCurrentCompanyId();
    public void pay(String number);
    public CompanyDTO getCurrentCompany();
}
