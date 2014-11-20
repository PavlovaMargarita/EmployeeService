package com.itechart.repository;

import com.itechart.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by marharyta.pavlova on 20.11.2014.
 */
public interface CountryRepository extends JpaRepository<Country, Long> {
}
