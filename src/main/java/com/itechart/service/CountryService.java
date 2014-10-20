package com.itechart.service;

import com.itechart.dto.CountryDTO;

import java.util.List;
/**
 * Created by Margarita on 20.10.2014.
 */
public interface CountryService {
    public List<CountryDTO> readCountryList();
}
