package com.itechart.service.impl;

import com.itechart.dto.AddressDTO;
import com.itechart.dto.CountryDTO;
import com.itechart.model.Address;
import com.itechart.model.Country;
import com.itechart.repository.AddressRepository;
import com.itechart.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Margarita on 20.10.2014.
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public List<AddressDTO> readAddressList() {
        List <Address> addressList = addressRepository.findAll();
        List <AddressDTO> addressDTOList = new ArrayList();
        for(Address address: addressList){
            addressDTOList.add(addressToAddressDTO(address));
        }
        return addressDTOList;
    }

    private AddressDTO addressToAddressDTO(Address address){
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        CountryDTO countryDTO = countryToCountryDTO(address.getCountry());
        addressDTO.setCountry(countryDTO);
        addressDTO.setCity(address.getCity());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setHouse(address.getHouse());
        addressDTO.setFlat(address.getFlat());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(countryDTO.getCountry() + " г." + addressDTO.getCity() +
                " ул." + addressDTO.getStreet() + " д." + addressDTO.getHouse() + " оф." + addressDTO.getFlat());
        addressDTO.setFullAddress(stringBuilder.toString());
        return addressDTO;
    }

    private CountryDTO countryToCountryDTO(Country country){
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(country.getId());
        countryDTO.setCountry(country.getCountry());
        return countryDTO;
    }
}
