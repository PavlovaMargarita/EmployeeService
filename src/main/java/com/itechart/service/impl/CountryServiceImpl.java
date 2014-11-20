package com.itechart.service.impl;

import com.itechart.dto.CountryDTO;
import com.itechart.model.Country;
import com.itechart.repository.CountryRepository;
import com.itechart.service.CountryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;
    @Override
    public List<CountryDTO> readCountryList() {
        Logger.getLogger(CountryServiceImpl.class).info("Read Country List ");
        List <Country> countryList = countryRepository.findAll();
        List <CountryDTO> countryDTOList = new ArrayList(countryList.size());
        for(Country country: countryList){
            countryDTOList.add(countryToCountryDTO(country));
        }
        return countryDTOList;
    }

    private CountryDTO countryToCountryDTO(Country country){
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(country.getId());
        countryDTO.setCountry(country.getCountry());
        return countryDTO;
    }
}
