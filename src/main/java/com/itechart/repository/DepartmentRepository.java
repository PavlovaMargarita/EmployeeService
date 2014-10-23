package com.itechart.repository;

import com.itechart.model.Department;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Margarita on 19.10.2014.
 */
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query("select d from Department d where d.company.companyName=:company")
    public List<Department> readDepartmentList(@Param("company") String company,Pageable pageable);
}
