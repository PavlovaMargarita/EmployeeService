package com.itechart.repository;

import com.itechart.model.Country;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Margarita on 19.10.2014.
 */
public interface CountryRepository extends JpaRepository<Country, Long>{
    @Query("select c from Country c")
    public List<Country> readCountryList(Pageable pageable);
}
