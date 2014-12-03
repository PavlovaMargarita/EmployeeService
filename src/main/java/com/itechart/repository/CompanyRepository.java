package com.itechart.repository;

import com.itechart.model.hibernate.Address;
import com.itechart.model.hibernate.Company;
import com.itechart.model.hibernate.Department;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("select count(c) from Company c where c.canLogin = true")
    public int companyCount();

    @Query("select c from Company c where c.canLogin = true")
    public List<Company> readCompanyList(Pageable pageable);


    @Query("select d from Department d where d.company.id=:company")
    public List<Department> readDepartmentList(@Param("company") Long company,Pageable pageable);

    @Query("select d from Department d where d.id=:id")
    public Department readDepartment(@Param("id")Long id);

    @Query("select d.addressList from Department d where d.id=:id")
    public List<Address> readAddressList(@Param("id") Long id);

    @Query("select a from Address a where a.id=:id")
    public Address readAddress(@Param("id")Long id);
}
