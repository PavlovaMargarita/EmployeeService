package com.itechart.service.impl;

import com.itechart.dto.AddressDTO;
import com.itechart.dto.CountryDTO;
import com.itechart.dto.DepartmentDTO;
import com.itechart.model.Address;
import com.itechart.model.Country;
import com.itechart.model.Department;
import com.itechart.repository.DepartmentRepository;
import com.itechart.service.DepartmentService;
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
 * Created by Margarita on 20.10.2014.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDTO> readDepartmentList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String company = authority.get(1).getAuthority();
        Long companyId = Long.parseLong(company.substring(10));
        Pageable topTen = new PageRequest(0, 10);
        Logger.getLogger(DepartmentServiceImpl.class).info("Read Department List, first=" + 0 + ", count=" + 10);
        List <Department> departmentList = departmentRepository.readDepartmentList(companyId, topTen);
        List <DepartmentDTO> departmentDTOList = new ArrayList(departmentList.size());
        for(Department department: departmentList){
            departmentDTOList.add(departmentToDepartmentDTO(department));
        }
        return departmentDTOList;
    }

    @Override
    public List<AddressDTO> readAddressList(Long departmentId) {
        Logger.getLogger(DepartmentServiceImpl.class).info("Read Address List for Department");
        List <Address> addressList = departmentRepository.readAddressList(departmentId);
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
}
