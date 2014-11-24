package com.itechart.service.impl;

import com.itechart.dto.EmployeeDTO;
import com.itechart.dto.SearchResult;
import com.itechart.enumProperty.RoleEnum;
import com.itechart.model.*;
import com.itechart.model.Employee;
import com.itechart.params.CurrentEmployeeParam;
import com.itechart.repository.*;
import com.itechart.service.EmployeeService;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PositionInCompanyRepository positionInCompanyRepository;

    @Override
    @Transactional
    public List<EmployeeDTO> readEmployeeList(int pageNumber, int pageRecords) {
        Long companyId = CurrentEmployeeParam.getCurrentCompanyId();
        Pageable pageable = new PageRequest(pageNumber, pageRecords);

        Logger.getLogger(EmployeeServiceImpl.class).info(String.format("Read Employee List, page=%s, pageRecords=%s ", pageNumber, pageRecords));

        List<Employee> employeeList = employeeRepository.readEmployeeList(companyId, pageable);

        List<EmployeeDTO> employeeDTOList = new ArrayList<>(employeeList.size());
        for (Employee employee : employeeList) {
            employeeDTOList.add(employeeToEmployeeDTO(employee));
        }

        return employeeDTOList;
    }

    @Override
    public EmployeeDTO readEmployee(Long id) {
        Logger.getLogger(EmployeeService.class).info(String.format("Read Employee by id %s", id));

        Employee employee = employeeRepository.findOne(id);

        Logger.getLogger(EmployeeService.class).info("Return Employee" + employee.toString());

        return employeeToEmployeeDTO(employee);
    }

    @Override
    public Long createEmployee(EmployeeDTO employeeDTO) throws IOException, SolrServerException {
        Long companyId = CurrentEmployeeParam.getCurrentCompanyId();

        employeeDTO.setCompanyId(companyId);
        Employee employee = employeeDTOToEmployee(employeeDTO);

        Logger.getLogger(EmployeeService.class).info("Create Employee " + employee.toString());

        employee = employeeRepository.save(employee);
        saveToSolr(employee);

        return employee.getId();

    }

    @Override
    @Transactional
    public Long updateEmployee(EmployeeDTO employeeDTO) throws IOException, SolrServerException {
        Employee employee = employeeDTOToEmployee(employeeDTO);
        employee.setId(employeeDTO.getId());

        Logger.getLogger(EmployeeService.class).info("Update Employee " + employee.toString());

        employee = employeeRepository.save(employee);
        if (employee.getFired()) {
            deleteInSolr(employee);
        } else {
            saveToSolr(employee);
        }

        return employeeDTO.getId();
    }

    @Override
    public long employeeCount() {
        Long companyId = CurrentEmployeeParam.getCurrentCompanyId();

        Logger.getLogger(EmployeeServiceImpl.class).info("Read employee count");

        return employeeRepository.employeeCount(companyId);
    }

    @Override
    public void loadPhoto(MultipartFile photo, Long id) throws Exception {
        if (ImageIO.read(photo.getInputStream()) == null) {
            Logger.getLogger(EmployeeService.class).info(String.format("Try to save incorrect image. Employee id=%s", id));
            throw new Exception("Try to save incorrect image");
        } else {
            //create new photo path
            String location = "C:/apache-tomcat-7.0.56/webapps/EmployeeService/files/company/";
            StringBuilder photoPath = new StringBuilder(location);
            Long companyId = CurrentEmployeeParam.getCurrentCompanyId();
            photoPath.append(companyId);
            photoPath.append("/photoEmployee/");
            photoPath.append(id);
            photoPath.append("/");

            String fileName = photo.getOriginalFilename();
            File pathFile = new File(photoPath.toString());
            if (!pathFile.exists()) {
                if(!pathFile.mkdir()){
                    Logger.getLogger(EmployeeServiceImpl.class).error(String.format("Can not create directory for save image. Directory: %s", pathFile));
                    throw new Exception("Can not create directory for save image");
                }
            }

            pathFile = new File(photoPath.toString() + fileName);
            System.out.println(pathFile);

            //delete old image
            EmployeeDTO employeeDTO = readEmployee(id);
            if (!employeeDTO.getPhotoURL().equals("t"))
                Files.delete(Paths.get(photoPath.toString() + employeeDTO.getPhotoURL()));

            photo.transferTo(pathFile);

            //save url on database
            Logger.getLogger(EmployeeServiceImpl.class).info("Load file employee, id = " + employeeDTO.getId());
            employeeDTO.setPhotoURL(fileName);
            updateEmployee(employeeDTO);
        }

    }

    @Override
    public EmployeeDTO readCurrentEmployee() {
        Logger.getLogger(EmployeeService.class).info("Read current employee");
        Long employeeId = CurrentEmployeeParam.getCurrentEmployeeId();

        return readEmployee(employeeId);
    }

    @Override
    public List readRoleEnumForCurrentEmployee() {
        Long employeeId = CurrentEmployeeParam.getCurrentEmployeeId();
        EmployeeDTO employeeDTO = readEmployee(employeeId);
        Logger.getLogger(EmployeeServiceImpl.class).info(String.format("Read role enum list for employee with id=%s", employeeId));
        List<RoleEnum> roleList = new ArrayList<>();

        for (int i = 0; i < RoleEnum.values().length; i++) {
            if (RoleEnum.values()[i].equals(RoleEnum.ROLE_HRM) && (employeeDTO.getRole().equals(RoleEnum.ROLE_HRM) || employeeDTO.getRole().equals(RoleEnum.ROLE_ADMIN)))
                roleList.add(RoleEnum.values()[i]);
            if (RoleEnum.values()[i].equals(RoleEnum.ROLE_ADMIN) && (employeeDTO.getRole().equals(RoleEnum.ROLE_HRM) || employeeDTO.getRole().equals(RoleEnum.ROLE_CEO)))
                roleList.add(RoleEnum.values()[i]);
            if (RoleEnum.values()[i].equals(RoleEnum.ROLE_EMPLOYEE) && employeeDTO.getRole().equals(RoleEnum.ROLE_HRM))
                roleList.add(RoleEnum.values()[i]);
        }

        return roleList;
    }

    @Override
    public List readRoleEnum() {
        Logger.getLogger(EmployeeServiceImpl.class).info("Read role enum list");
        List<RoleEnum> roleList = new ArrayList<>();

        for (int i = 0; i < RoleEnum.values().length; i++) {
            if (RoleEnum.values()[i].equals(RoleEnum.ROLE_HRM))
                roleList.add(RoleEnum.values()[i]);
            if (RoleEnum.values()[i].equals(RoleEnum.ROLE_ADMIN))
                roleList.add(RoleEnum.values()[i]);
            if (RoleEnum.values()[i].equals(RoleEnum.ROLE_EMPLOYEE))
                roleList.add(RoleEnum.values()[i]);
        }

        return roleList;
    }

    @Override
    public void createEmployeeCeo(EmployeeDTO employeeDTO) throws IOException, SolrServerException {
        Employee employee = employeeDTOToEmployee(employeeDTO);
        employee.setFired(false);
        PositionInCompany positionInCompany = new PositionInCompany();
        positionInCompany.setCompany(employee.getCompany());
        positionInCompany.setPosition("CEO");
        positionInCompanyRepository.save(positionInCompany);
        employee.setPositionInCompany(positionInCompany);
        employee = employeeRepository.save(employee);
        Logger.getLogger(EmployeeServiceImpl.class).info("Create employee CEO:" + employee.toString());
        saveToSolr(employee);
    }

    @Override
    public EmployeeDTO readEmployeeCeoByCompanyId(Long companyId) {
        Logger.getLogger(EmployeeServiceImpl.class).info(String.format("Read employee CEO for company with id=%s",companyId));
        Employee employee = employeeRepository.readEmployeeCeo(companyId);
        return employeeToEmployeeDTO(employee);
    }

    @Override
    public SearchResult search(String searchValue, int page, int pageRecords) throws SolrServerException {
        Logger.getLogger(EmployeeServiceImpl.class).info(String.format("Search employee with searchValue=%s, page=%s, pageRecords=%s",searchValue, page, pageRecords));
        String companyId = CurrentEmployeeParam.getCurrentCompanyId().toString();
        SolrDocumentList solrDocuments = searchInSolr(searchValue, companyId, page, pageRecords);
        SearchResult searchResult = new SearchResult();

        List<Long> idList = new ArrayList<>(solrDocuments.size());
        for (SolrDocument solrDocument : solrDocuments) {
            idList.add(Long.parseLong(solrDocument.get("id").toString()));
        }
        List<Employee> employeeList = employeeRepository.findByIdIn(idList);

        List<EmployeeDTO> employeeDTOList = new ArrayList<>(employeeList.size());
        for (Employee employee : employeeList) {
            employeeDTOList.add(employeeToEmployeeDTO(employee));
        }

        searchResult.setEmployeeList(employeeDTOList);
        searchResult.setTotalSearchCount(solrDocuments.getNumFound());
        return searchResult;
    }

    private EmployeeDTO employeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setF_name(employee.getFirstName());
        employeeDTO.setS_name(employee.getLastName());
        if (employee.getDepartment() != null) {
            employeeDTO.setDepartmentId(employee.getDepartment().getId());
            employeeDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
        }
        employeeDTO.setDateOfBirth(employee.getDateOfBirth());
        employeeDTO.setSex(employee.getSex());
        employeeDTO.setCountryId(employee.getCountry().getId());
        employeeDTO.setCity(employee.getCity());
        employeeDTO.setStreet(employee.getStreet());
        employeeDTO.setHouse(employee.getHouse());
        employeeDTO.setFlat(employee.getFlat());
        employeeDTO.setPositionInCompanyId(employee.getPositionInCompany().getId());
        if (employee.getAddress() != null) {
            employeeDTO.setAddressId(employee.getAddress().getId());
        }
        employeeDTO.setDateContractEnd(employee.getDateContractEnd());
        employeeDTO.setFired(employee.getFired());
        employeeDTO.setFiredComment(employee.getFiredComment());
        employeeDTO.setPhotoURL(employee.getPhotoURL());
        employeeDTO.setLogin(employee.getLogin());
        employeeDTO.setPassword(employee.getPassword());
        employeeDTO.setRole(employee.getRole());
        employeeDTO.setCompanyId(employee.getCompany().getId());
        employeeDTO.setEmail(employee.getEmail());
        return employeeDTO;
    }

    private Employee employeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getF_name());
        employee.setLastName(employeeDTO.getS_name());
        employee.setDateOfBirth(employeeDTO.getDateOfBirth());
        employee.setSex(employeeDTO.getSex());
        Country country = countryRepository.findOne(employeeDTO.getCountryId());
        employee.setCountry(country);
        employee.setCity(employeeDTO.getCity());
        employee.setStreet(employeeDTO.getStreet());
        employee.setHouse(employeeDTO.getHouse());
        employee.setFlat(employeeDTO.getFlat());
        employee.setPhotoURL(employeeDTO.getPhotoURL());
        if (employeeDTO.getAddressId() != null) {
            Address address = companyRepository.readAddress(employeeDTO.getAddressId());
            employee.setAddress(address);
        }
        if (employeeDTO.getDepartmentId() != null) {
            Department department = companyRepository.readDepartment(employeeDTO.getDepartmentId());
            employee.setDepartment(department);
        }
        if (employeeDTO.getPositionInCompanyId() != null) {
            PositionInCompany positionInCompany = positionInCompanyRepository.findOne(employeeDTO.getPositionInCompanyId());
            employee.setPositionInCompany(positionInCompany);
        }

        employee.setDateContractEnd(employeeDTO.getDateContractEnd());
        employee.setFired(employeeDTO.getFired());
        employee.setFiredComment(employeeDTO.getFiredComment());
        employee.setDateFired(employeeDTO.getDateFired());
        employee.setLogin(employeeDTO.getLogin());
        employee.setPassword(employeeDTO.getPassword());
        employee.setRole(employeeDTO.getRole());
        employee.setCompany(companyRepository.findOne(employeeDTO.getCompanyId()));
        employee.setEmail(employeeDTO.getEmail());
        return employee;
    }

    private void saveToSolr(Employee employee) throws IOException, SolrServerException {
        // save to solr
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id", employee.getId().toString());
        doc.addField("first_name", employee.getFirstName());
        doc.addField("last_name", employee.getLastName());
        doc.addField("email", employee.getEmail());
        doc.addField("companyId", employee.getCompany().getId());
        SolrServer solrServer = new HttpSolrServer("http://localhost:8984/solr");
        solrServer.add(doc);
        solrServer.commit();
        solrServer.shutdown();

    }

    private void deleteInSolr(Employee employee) throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://localhost:8984/solr");
        solrServer.deleteById(employee.getId().toString());
        solrServer.commit();
        solrServer.shutdown();
    }


    private SolrDocumentList searchInSolr(String value, String companyId, int page, int pageRecords) throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://localhost:8984/solr");
        SolrQuery query = new SolrQuery();
        query.setQuery(String.format("(first_name:%s* OR last_name:%s* OR email:%s*) AND companyId:%s", value, value, value, companyId));

        query.setStart(page * pageRecords);
        query.setRows(pageRecords);
        query.setFields("id");

        QueryResponse response = solrServer.query(query);
        solrServer.shutdown();
        return response.getResults();
    }

}
