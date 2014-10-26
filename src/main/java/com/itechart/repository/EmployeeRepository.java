package com.itechart.repository;

import com.itechart.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/**
 * Created by Margarita on 19.10.2014.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from Employee e where e.fired = false and e.department.company.id=:company order by e.dateContractEnd asc")
    public List<Employee> readEmployeeList(@Param("company") Long company, Pageable pageable);
}
