package com.itechart.repository;

import com.itechart.model.hibernate.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
