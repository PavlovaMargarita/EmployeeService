package com.itechart.service.impl;

import com.itechart.dto.AddressDTO;
import com.itechart.dto.CountryDTO;
import com.itechart.dto.DepartmentDTO;
import com.itechart.dto.PositionInCompanyDTO;
import com.itechart.model.Address;
import com.itechart.model.Country;
import com.itechart.model.Department;
import com.itechart.model.PositionInCompany;
import com.itechart.repository.CompanyRepository;
import com.itechart.service.CompanyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Margarita on 16.10.2014.
 */
@Service
public class CompanyServiceImpl implements CompanyService {


    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<PositionInCompanyDTO> readPositionInCompanyList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String company = authority.get(1).getAuthority();
        Long companyId = Long.parseLong(company.substring(10));
        Pageable topTen = new PageRequest(0, 10);
        Logger.getLogger(CompanyServiceImpl.class).info("Read PositionInCompanyList List, first=" + 0 + ", count=" + 10);
        List<PositionInCompany> positionInCompanyList =companyRepository.readPositionInCompanyList(companyId, topTen);
        List<PositionInCompanyDTO> positionInCompanyDTOList = new ArrayList(positionInCompanyList.size());
        for(PositionInCompany positionInCompany: positionInCompanyList){
            positionInCompanyDTOList.add(positionInCompanyToPositionInCompanyDTO(positionInCompany));
        }
        return positionInCompanyDTOList;
    }

    @Override
    public List<DepartmentDTO> readDepartmentList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String company = authority.get(1).getAuthority();
        Long companyId = Long.parseLong(company.substring(10));
        Pageable topTen = new PageRequest(0, 10);
        Logger.getLogger(CompanyServiceImpl.class).info("Read Department List, first=" + 0 + ", count=" + 10);
        List <Department> departmentList = companyRepository.readDepartmentList(companyId, topTen);
        List <DepartmentDTO> departmentDTOList = new ArrayList(departmentList.size());
        for(Department department: departmentList){
            departmentDTOList.add(departmentToDepartmentDTO(department));
        }
        return departmentDTOList;
    }

    @Override
    public List<AddressDTO> readAddressList(Long departmentId) {
        Logger.getLogger(CompanyServiceImpl.class).info("Read Address List for Department");
        List <Address> addressList = companyRepository.readAddressList(departmentId);
        List <AddressDTO> addressDTOList = new ArrayList(addressList.size());
        for(Address address: addressList){
            addressDTOList.add(addressToAddressDTO(address));
        }
        return addressDTOList;
    }

    private DepartmentDTO departmentToDepartmentDTO(Department department){
        DepartmentDTO departmentDTO = new DepartmentDTO();
        if(department != null) {
            departmentDTO.setId(department.getId());
            departmentDTO.setDepartment(department.getDepartmentName());
        }
        return departmentDTO;
    }

    private AddressDTO addressToAddressDTO(Address address){
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        CountryDTO countryDTO = countryToCountryDTO(address.getCountry());
        addressDTO.setCountry(countryDTO);
        addressDTO.setCity(address.getCity());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setHouse(address.getHouse());
        addressDTO.setFlat(address.getFlat());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(countryDTO.getCountry() + " г." + addressDTO.getCity() +
                " ул." + addressDTO.getStreet() + " д." + addressDTO.getHouse() + " оф." + addressDTO.getFlat());
        addressDTO.setFullAddress(stringBuilder.toString());
        return addressDTO;
    }

    private CountryDTO countryToCountryDTO(Country country){
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(country.getId());
        countryDTO.setCountry(country.getCountry());
        return countryDTO;
    }
    private PositionInCompanyDTO positionInCompanyToPositionInCompanyDTO(PositionInCompany positionInCompany){
        PositionInCompanyDTO positionInCompanyDTO = new PositionInCompanyDTO();
        positionInCompanyDTO.setId(positionInCompany.getId());
        positionInCompanyDTO.setPosition(positionInCompany.getPosition());
        return positionInCompanyDTO;
    }
}
