package com.itechart.repository;

import com.itechart.model.PositionInCompany;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Margarita on 19.10.2014.
 */
public interface PositionInCompanyRepository extends JpaRepository<PositionInCompany, Long> {
    @Query("select p from PositionInCompany p where p.company.companyName=:company")
    public List<PositionInCompany> readPositionInCompanyList(@Param("company")String company, Pageable pageable);
}
