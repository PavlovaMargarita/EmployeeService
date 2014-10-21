package com.itechart.repository;

import com.itechart.enumProperty.SexEnum;
import com.itechart.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

/**
 * Created by Margarita on 19.10.2014.
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Modifying
    @Query("update Employee e set e.f_name=:f_name, e.s_name=:s_name, e.dateOfBirth=:dateOfBirth,e.sex=:sex, e.country=:country," +
            "e.city=:city, e.street=:street, e.house=:house, e.flat=:flat, e.photoURL=:photoURL, e.address=:address," +
            "e.department=:department, e.positionInCompany=:positionInCompany  where e.id =:id")
    public int updateEmployee(@Param("id") Long id, @Param("f_name") String f_name, @Param("s_name") String s_name, @Param("dateOfBirth") Date dateOfBirth,
                              @Param("sex") SexEnum sex, @Param("country") Country country, @Param("city") String city, @Param("street") String street,
                              @Param("house") Integer house, @Param("flat") String flat, @Param("photoURL") String photoURL, @Param("address") Address address,
                              @Param("department")Department department, @Param("positionInCompany")PositionInCompany positionInCompany);


}
