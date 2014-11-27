package com.itechart.service;

import com.itechart.model.dto.CountryDTO;

import java.util.List;

public interface CountryService {
    public List<CountryDTO> readCountryList();
}
