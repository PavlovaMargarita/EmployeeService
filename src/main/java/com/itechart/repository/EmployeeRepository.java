package com.itechart.repository;

import com.itechart.model.hibernate.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e where e.fired = false and e.company.id=:company and e.role <> 'ROLE_CEO' order by e.dateContractEnd asc")
    public List<Employee> readEmployeeList(@Param("company") Long company, Pageable pageable);

    @Query("select count(e) from Employee e where e.fired = false and e.department.company.id=:companyId")
    public long employeeCount(@Param("companyId") Long companyId);

    @Query("SELECT e FROM Employee e WHERE e.login = :login")
    public Employee readEmployee(@Param("login") String login);

    @Query("SELECT e FROM Employee e WHERE e.company.id=:companyId and e.role='ROLE_CEO'")
    public Employee readEmployeeCeo(@Param("companyId") Long companyId);

    public List<Employee> findByIdIn(Collection<Long> idList);
}
