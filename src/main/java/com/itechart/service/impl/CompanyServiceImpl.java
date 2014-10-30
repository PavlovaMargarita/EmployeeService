package com.itechart.service.impl;

import com.itechart.dto.*;
import com.itechart.enumProperty.CompanyStatusEnum;
import com.itechart.enumProperty.SexEnum;
import com.itechart.model.*;
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

    private final int COUNT_COMPANY_FOR_SELECT = 50;
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

    @Override
    public List<CompanyDTO> readCompanyList() {
        int count = companyRepository.companyCount();
        int totalSelect = count / COUNT_COMPANY_FOR_SELECT + 1;
        List<Company> companyList = new ArrayList<>(count);
        for(int i = 0; i < totalSelect; i++){
            companyList.addAll(companyRepository.readCompanyList(new PageRequest(i, COUNT_COMPANY_FOR_SELECT)));
        }
        List<CompanyDTO> companyDTOList = new ArrayList<>(count);
        for (Company company: companyList){
            companyDTOList.add(companyToCompanyDTO(company));
        }
        return companyDTOList;
    }

    @Override
    public void updateCompanyEveryDay(CompanyDTO companyDTO) {
        Company company = companyDTOToCompany(companyDTO);
        company.setId(companyDTO.getId());
        companyRepository.save(company);
    }

    @Override
    public void updateCompany(CompanyDTO companyDTO) {
        Company company = companyDTOToCompany(companyDTO);
        company.setId(companyDTO.getId());
        company.setAccountSum(companyDTO.getAccountSum() + companyDTO.getAddSum());
        switch (companyDTO.getCompanyStatus()){
            case CONTINUE_FUNCTIONING: companyRepository.save(company); break;
            case SUSPEND_FUNCTIONING: companyRepository.save(company); break;
            case FINISH_FUNCTIONING: company.setCanLogin(false);
                                     companyRepository.save(company);
        }
    }

    @Override
    public List<CompanyDTO> readCompanyList(int pageNumber, int pageRecords) {
        Pageable recordsCount = new PageRequest(pageNumber, pageRecords);
        List<Company> companyList = companyRepository.readCompanyList(recordsCount);
        List<CompanyDTO> companyDTOList = new ArrayList(companyList.size());
        for(Company company: companyList){
               companyDTOList.add(companyToCompanyDTO(company));
        }
        return companyDTOList;
    }

    @Override
    public long companyCount() {
        return companyRepository.companyCount();
    }

    @Override
    public List<CompanyStatusDTO> readCompanyStatusEnum() {
        List<CompanyStatusDTO> companyTaskEnum = new ArrayList(SexEnum.values().length);
        for(int i = 0; i < CompanyStatusEnum.values().length; i++) {
            CompanyStatusDTO companyStatusDTO = new CompanyStatusDTO();
            companyStatusDTO.setCompanyStatusEnum(CompanyStatusEnum.values()[i]);
            if(companyStatusDTO.getCompanyStatusEnum().equals(CompanyStatusEnum.CONTINUE_FUNCTIONING)){
                companyStatusDTO.setCompanyStatusEnumRussian("Продолжить функционирование");
            }
            if(companyStatusDTO.getCompanyStatusEnum().equals(CompanyStatusEnum.SUSPEND_FUNCTIONING)){
                companyStatusDTO.setCompanyStatusEnumRussian("Приостановить функционирование");
            }
            if(companyStatusDTO.getCompanyStatusEnum().equals(CompanyStatusEnum.FINISH_FUNCTIONING)){
                companyStatusDTO.setCompanyStatusEnumRussian("Завершить функционирование");
            }
            companyTaskEnum.add(companyStatusDTO);
        }
        return companyTaskEnum;
    }

    @Override
    public CompanyDTO readCompany(Long id) {
        Company company = companyRepository.findOne(id);
        CompanyDTO companyDTO = companyToCompanyDTO(company);
        return companyDTO;
    }

    @Override
    public void createCompany(CompanyDTO companyDTO) {
        Company company = companyDTOToCompany(companyDTO);
        company.setAccountSum(companyDTO.getAddSum());
        company.setCanLogin(true);
        company.setCompanyStatus(CompanyStatusEnum.CONTINUE_FUNCTIONING);
        companyRepository.save(company);
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

    private CompanyDTO companyToCompanyDTO(Company company){
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setCompanyName(company.getCompanyName());
        companyDTO.setAccountSum(company.getAccountSum());
        companyDTO.setDateBoundaryRefill(company.getDateBoundaryRefill());
        companyDTO.setCanLogin(company.getCanLogin());
        companyDTO.setCompanyStatus(company.getCompanyStatus());
        return companyDTO;
    }

    private Company companyDTOToCompany(CompanyDTO companyDTO){
        Company company = new Company();
        company.setCompanyName(companyDTO.getCompanyName());
        company.setAccountSum(companyDTO.getAccountSum());
        company.setDateBoundaryRefill(companyDTO.getDateBoundaryRefill());
        company.setCanLogin(companyDTO.getCanLogin());
        company.setCompanyStatus(companyDTO.getCompanyStatus());
        return company;
    }
}
