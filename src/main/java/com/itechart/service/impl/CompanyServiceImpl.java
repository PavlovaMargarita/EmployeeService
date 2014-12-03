package com.itechart.service.impl;

import com.itechart.model.dto.*;
import com.itechart.enumProperty.CompanyStatusEnum;
import com.itechart.enumProperty.SexEnum;
import com.itechart.model.hibernate.*;
import com.itechart.params.SecurityWrapper;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Long companyId = SecurityWrapper.getCurrentCompanyId();
        Pageable pageable = new PageRequest(0, 10);

        Logger.getLogger(CompanyServiceImpl.class).info("Read PositionInCompany List");

        List<PositionInCompany> positionInCompanyList = positionInCompanyRepository.readPositionInCompanyList(companyId, pageable);

        List<PositionInCompanyDTO> positionInCompanyDTOList = new ArrayList<>(positionInCompanyList.size());
        for (PositionInCompany positionInCompany : positionInCompanyList) {
            positionInCompanyDTOList.add(positionInCompanyToPositionInCompanyDTO(positionInCompany));
        }

        return positionInCompanyDTOList;
    }

    @Override
    public List<DepartmentDTO> readDepartmentList() {
        Long companyId = SecurityWrapper.getCurrentCompanyId();
        Pageable pageable = new PageRequest(0, 10);

        Logger.getLogger(CompanyServiceImpl.class).info("Read Department List");

        List<Department> departmentList = companyRepository.readDepartmentList(companyId, pageable);

        List<DepartmentDTO>  departmentDTOList = new ArrayList<>(departmentList.size());
        for (Department department : departmentList) {
            departmentDTOList.add(departmentToDepartmentDTO(department));
        }

        return departmentDTOList;
    }

    @Override
    public List<AddressDTO> readAddressList(Long departmentId) {
        Logger.getLogger(CompanyServiceImpl.class).info(String.format("Read Address List for Department Id = %s", departmentId));

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
        Logger.getLogger(CompanyServiceImpl.class).info("Update company: " + company.toString());

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

        Logger.getLogger(CompanyServiceImpl.class).info("Read company list, pageNumber=" + pageNumber + ", pageRecords=" + pageRecords);

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
        Logger.getLogger(CompanyServiceImpl.class).info("Read company status list");

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
    public CompanyDTO readCompanyById(Long id) {
        Logger.getLogger(CompanyServiceImpl.class).info("Read company by id = " + id);

        Company company = companyRepository.findOne(id);

        return companyToCompanyDTO(company);
    }

    @Override
    public Long createCompany(CompanyDTO companyDTO) {
        Company company = companyDTOToCompany(companyDTO);
        company.setCanLogin(true);
        company.setCompanyStatus(CompanyStatusEnum.CONTINUE_FUNCTIONING);

        Logger.getLogger(CompanyServiceImpl.class).info("Create company: " + company.toString());

        company = companyRepository.save(company);

        return company.getId();
    }

    @Override
    public Date getDateBoundaryRefill() {
        Long companyId = SecurityWrapper.getCurrentCompanyId();
        CompanyDTO companyDTO = readCompanyById(companyId);

        Logger.getLogger(CompanyServiceImpl.class).info("Get date boundary refill");

        return companyDTO.getDateBoundaryRefill();
    }

    @Override
    public void pay(String number) throws Exception {
        Pattern pattern = Pattern.compile("^((([0-9]){3}){1}/(19|20)?[0-9]{2}(0?[1-9]|[12][0-9][3][01]){2}(0?[1-9]|1[012]){2})");
        Matcher matcher = pattern.matcher(number);
        if(matcher.matches()){
            if(accountNumberRepository.readAccountNumber(number) == null){
                Logger.getLogger(CompanyServiceImpl.class).info(String.format("Pay with accountNumber=%s",number));
                AccountNumber accountNumber = new AccountNumber();
                accountNumber.setNumber(number);
                accountNumberRepository.save(accountNumber);
                Long companyId = SecurityWrapper.getCurrentCompanyId();
                CompanyDTO companyDTO = readCompanyById(companyId);
                java.util.Date companyDate = new java.util.Date(companyDTO.getDateBoundaryRefill().getTime());
                Calendar cal = Calendar.getInstance();
                cal.setTime(companyDate);
                cal.add(Calendar.MONTH, 1);
                companyDTO.setDateBoundaryRefill(new java.sql.Date((cal.getTime()).getTime()));
                this.updateCompany(companyDTO);
            } else{
                throw  new Exception("This account number has already been entered previously");
            }
        } else{
            throw  new Exception("Incorrect account number");
        }
    }

    @Override
    public CompanyDTO getCurrentCompany() {
        Logger.getLogger(CompanyServiceImpl.class).info("Read current company");
        Long companyId = SecurityWrapper.getCurrentCompanyId();
        return readCompanyById(companyId);
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
        company.setDateBoundaryRefill(companyDTO.getDateBoundaryRefill());
        company.setCanLogin(companyDTO.getCanLogin());
        company.setCompanyStatus(companyDTO.getCompanyStatus());
        company.setProgramCost(companyDTO.getProgramCost());
        company.setCompanyPlan(companyDTO.getCompanyPlan());
        return company;
    }

}
