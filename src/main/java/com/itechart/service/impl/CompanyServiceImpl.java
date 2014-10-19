package com.itechart.service.impl;

import com.itechart.dao.CountryDAO;
import com.itechart.model.Country;
import com.itechart.repository.CountryRepository;
import com.itechart.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Margarita on 16.10.2014.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CountryRepository countryRepository;

    @Override
    @Transactional
    public void createCountry(Country country) {
        countryRepository.save(country);
    }
}
