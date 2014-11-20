package com.itechart.repository;

import com.itechart.model.PositionInCompany;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PositionInCompanyRepository extends JpaRepository<PositionInCompany, Long> {
    @Query("select p from PositionInCompany p where p.company.id=:company and p.position <> 'CEO'")
    public List<PositionInCompany> readPositionInCompanyList(@Param("company")Long company, Pageable pageable);

}
