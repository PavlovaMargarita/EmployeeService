package com.itechart.repository;

import com.itechart.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Margarita on 19.10.2014.
 */
public interface CountryRepository extends JpaRepository<Country, Long>{
}
