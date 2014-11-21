package com.itechart.service.impl;

import com.itechart.dto.*;
import com.itechart.enumProperty.CompanyStatusEnum;
import com.itechart.enumProperty.SexEnum;
import com.itechart.model.*;
import com.itechart.params.CurrentEmployeeParam;
import com.itechart.repository.AccountNumberRepository;
import com.itechart.repository.CompanyRepository;
import com.itechart.repository.PositionInCompanyRepository;
import com.itechart.service.CompanyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AccountNumberRepository accountNumberRepository;

    @Autowired
    private PositionInCompanyRepository positionInCompanyRepository;

    @Override
    public List<PositionInCompanyDTO> readPositionInCompanyList() {
        Long companyId = CurrentEmployeeParam.getCurrentCompanyId();
        Pageable topTen = new PageRequest(0, 10);
        Logger.getLogger(CompanyServiceImpl.class).info("Read PositionInCompanyList");
        List<PositionInCompany> positionInCompanyList = positionInCompanyRepository.readPositionInCompanyList(companyId, topTen);
        List<PositionInCompanyDTO> positionInCompanyDTOList = new ArrayList<>(positionInCompanyList.size());
        for (PositionInCompany positionInCompany : positionInCompanyList) {
            positionInCompanyDTOList.add(positionInCompanyToPositionInCompanyDTO(positionInCompany));
        }
        return positionInCompanyDTOList;
    }

    @Override
    public List<DepartmentDTO> readDepartmentList() {
        Long companyId = CurrentEmployeeParam.getCurrentCompanyId();
        Pageable topTen = new PageRequest(0, 10);
        Logger.getLogger(CompanyServiceImpl.class).info("Read Department ");
        List<Department> departmentList = companyRepository.readDepartmentList(companyId, topTen);
        List<DepartmentDTO>  departmentDTOList = new ArrayList<>(departmentList.size());
        for (Department department : departmentList) {
            departmentDTOList.add(departmentToDepartmentDTO(department));
        }
        return departmentDTOList;
    }

    @Override
    public List<AddressDTO> readAddressList(Long departmentId) {
        Logger.getLogger(CompanyServiceImpl.class).info("Read Address List for Department");
        List<Address> addressList = companyRepository.readAddressList(departmentId);
        List<AddressDTO> addressDTOList = new ArrayList<>(addressList.size());
        for (Address address : addressList) {
            addressDTOList.add(addressToAddressDTO(address));
        }
        return addressDTOList;
    }

    @Override
    public void updateCompany(CompanyDTO companyDTO) {
        Company company = companyDTOToCompany(companyDTO);
        company.setId(companyDTO.getId());
//        company.setAccountSum(companyDTO.getAccountSum() + companyDTO.getAddSum());
//        java.util.Date currentDate = new java.util.Date();
//        java.util.Date companyDate = new java.util.Date(company.getDateBoundaryRefill().getTime());
//        Calendar cal = Calendar.getInstance();
//        while (currentDate.after(companyDate) && company.getAccountSum() - Params.COST_OF_USING_MONTH >= 0) {
//            company.setAccountSum(company.getAccountSum() - Params.COST_OF_USING_MONTH);
//            cal.setTime(companyDate);
//            cal.add(Calendar.MONTH, 1);
//            company.setDateBoundaryRefill(new java.sql.Date((cal.getTime()).getTime()));
//            companyDate = new java.util.Date(company.getDateBoundaryRefill().getTime());
//        }
        switch (companyDTO.getCompanyStatus()) {
            case CONTINUE_FUNCTIONING:
                Logger.getLogger(CompanyServiceImpl.class).info("Update company:" + company.toString());
                companyRepository.save(company);
                break;
            case SUSPEND_FUNCTIONING:
                companyRepository.save(company);
                Logger.getLogger(CompanyServiceImpl.class).info("Update company :" + company.toString());
                break;
            case FINISH_FUNCTIONING:
                company.setCanLogin(false);
                Logger.getLogger(CompanyServiceImpl.class).info("Update company:" + company.toString());
                companyRepository.save(company);
                break;
        }
    }

    @Override
    public List<CompanyDTO> readCompanyList(int pageNumber, int pageRecords) {
        Pageable recordsCount = new PageRequest(pageNumber, pageRecords);
        Logger.getLogger(CompanyServiceImpl.class).info("Read company list, page = " + pageNumber);
        List<Company> companyList = companyRepository.readCompanyList(recordsCount);
        List<CompanyDTO> companyDTOList = new ArrayList<>(companyList.size());
        for (Company company : companyList) {
            companyDTOList.add(companyToCompanyDTO(company));
        }
        return companyDTOList;
    }

    @Override
    public long companyCount() {
        Logger.getLogger(CompanyServiceImpl.class).info("Read company count ");
        return companyRepository.companyCount();
    }

    @Override
    public List<CompanyStatusDTO> readCompanyStatusEnum() {
        List<CompanyStatusDTO> companyTaskEnum = new ArrayList<>(SexEnum.values().length);
        for (int i = 0; i < CompanyStatusEnum.values().length; i++) {
            CompanyStatusDTO companyStatusDTO = new CompanyStatusDTO();
            companyStatusDTO.setCompanyStatusEnum(CompanyStatusEnum.values()[i]);
            if (companyStatusDTO.getCompanyStatusEnum().equals(CompanyStatusEnum.CONTINUE_FUNCTIONING)) {
                companyStatusDTO.setCompanyStatusEnumRussian("Продолжить функционирование");
            }
            if (companyStatusDTO.getCompanyStatusEnum().equals(CompanyStatusEnum.SUSPEND_FUNCTIONING)) {
                companyStatusDTO.setCompanyStatusEnumRussian("Приостановить функционирование");
            }
            if (companyStatusDTO.getCompanyStatusEnum().equals(CompanyStatusEnum.FINISH_FUNCTIONING)) {
                companyStatusDTO.setCompanyStatusEnumRussian("Завершить функционирование");
            }
            companyTaskEnum.add(companyStatusDTO);
        }
        return companyTaskEnum;
    }

    @Override
    public CompanyDTO readCompany(Long id) {
        Logger.getLogger(CompanyServiceImpl.class).info("Read company by id = " + id);
        Company company = companyRepository.findOne(id);
        return companyToCompanyDTO(company);
    }

    @Override
    public Long createCompany(CompanyDTO companyDTO) {
        Company company = companyDTOToCompany(companyDTO);
//        company.setAccountSum(companyDTO.getAddSum());
        company.setCanLogin(true);
        company.setCompanyStatus(CompanyStatusEnum.CONTINUE_FUNCTIONING);
        Logger.getLogger(CompanyServiceImpl.class).info("Create company: " + company.toString());
        company = companyRepository.save(company);

        return company.getId();
    }

    @Override
    public Date getDateBoundaryRefill() {
        Long companyId = CurrentEmployeeParam.getCurrentCompanyId();
        CompanyDTO companyDTO = readCompany(companyId);
        Logger.getLogger(CompanyServiceImpl.class).info("Get date boundary refill");
        return companyDTO.getDateBoundaryRefill();
    }

    @Override
    public Long getCurrentCompanyId() {
        return CurrentEmployeeParam.getCurrentCompanyId();
    }

    @Override
    public void pay(String number) {
        if(accountNumberRepository.readAccountNumber(number) == null){
            AccountNumber accountNumber = new AccountNumber();
            accountNumber.setNumber(number);
            accountNumberRepository.save(accountNumber);
            Long companyId = CurrentEmployeeParam.getCurrentCompanyId();
            CompanyDTO companyDTO = readCompany(companyId);
            java.util.Date companyDate = new java.util.Date(companyDTO.getDateBoundaryRefill().getTime());
            Calendar cal = Calendar.getInstance();
            cal.setTime(companyDate);
            cal.add(Calendar.MONTH, 1);
            companyDTO.setDateBoundaryRefill(new java.sql.Date((cal.getTime()).getTime()));
            this.updateCompany(companyDTO);
        }
    }

    @Override
    public CompanyDTO getCurrentCompany() {
        Long companyId = CurrentEmployeeParam.getCurrentCompanyId();
        return readCompany(companyId);
    }

    private DepartmentDTO departmentToDepartmentDTO(Department department) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        if (department != null) {
            departmentDTO.setId(department.getId());
            departmentDTO.setDepartment(department.getDepartmentName());
        }
        return departmentDTO;
    }

    private AddressDTO addressToAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        CountryDTO countryDTO = countryToCountryDTO(address.getCountry());
        addressDTO.setCountry(countryDTO);
        addressDTO.setCity(address.getCity());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setHouse(address.getHouse());
        addressDTO.setFlat(address.getFlat());
        String fullAddress = countryDTO.getCountry() + " г." + addressDTO.getCity() +
                " ул." + addressDTO.getStreet() + " д." + addressDTO.getHouse() + " оф." + addressDTO.getFlat();
        addressDTO.setFullAddress(fullAddress);
        return addressDTO;
    }

    private CountryDTO countryToCountryDTO(Country country) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(country.getId());
        countryDTO.setCountry(country.getCountry());
        return countryDTO;
    }

    private PositionInCompanyDTO positionInCompanyToPositionInCompanyDTO(PositionInCompany positionInCompany) {
        PositionInCompanyDTO positionInCompanyDTO = new PositionInCompanyDTO();
        positionInCompanyDTO.setId(positionInCompany.getId());
        positionInCompanyDTO.setPosition(positionInCompany.getPosition());
        return positionInCompanyDTO;
    }

    private CompanyDTO companyToCompanyDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        companyDTO.setId(company.getId());
        companyDTO.setCompanyName(company.getCompanyName());
//        companyDTO.setAccountSum(company.getAccountSum());
        companyDTO.setDateBoundaryRefill(company.getDateBoundaryRefill());
        companyDTO.setCanLogin(company.getCanLogin());
        companyDTO.setCompanyStatus(company.getCompanyStatus());
        companyDTO.setProgramCost(company.getProgramCost());
        companyDTO.setCompanyPlan(company.getCompanyPlan());
        companyDTO.setEmployeeCount(company.getEmployeeList().size());
        return companyDTO;
    }

    private Company companyDTOToCompany(CompanyDTO companyDTO) {
        Company company = new Company();
        company.setCompanyName(companyDTO.getCompanyName());
//        company.setAccountSum(companyDTO.getAccountSum());
        company.setDateBoundaryRefill(companyDTO.getDateBoundaryRefill());
        company.setCanLogin(companyDTO.getCanLogin());
        company.setCompanyStatus(companyDTO.getCompanyStatus());
        company.setProgramCost(companyDTO.getProgramCost());
        company.setCompanyPlan(companyDTO.getCompanyPlan());
        return company;
    }


}
