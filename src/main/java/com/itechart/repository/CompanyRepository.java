package com.itechart.repository;

import com.itechart.model.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Margarita on 19.10.2014.
 */
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("select c from Company c")
    public List<Company> readCompanyList(Pageable pageable);
}
