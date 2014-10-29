package com.itechart.service;

import com.itechart.dto.AddressDTO;
import com.itechart.dto.CompanyDTO;
import com.itechart.dto.DepartmentDTO;
import com.itechart.dto.PositionInCompanyDTO;

import java.util.List;

/**
 * Created by Margarita on 16.10.2014.
 */
public interface CompanyService {
    public List<PositionInCompanyDTO> readPositionInCompanyList();
    public List<DepartmentDTO> readDepartmentList();
    public List<AddressDTO> readAddressList(Long departmentId);
    public List<CompanyDTO> readCompanyList();
    public void updateCompany(CompanyDTO companyDTO);
}
