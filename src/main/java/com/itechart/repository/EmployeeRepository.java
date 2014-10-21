package com.itechart.repository;

import com.itechart.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Margarita on 19.10.2014.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Modifying
    @Query("update Employee e set e.f_name =:f_name where e.id =:id")
    public int updateEmployee(@Param("id") Long id, @Param("f_name") String f_name);
}
