package com.itechart.dto;

/**
 * Created by Margarita on 20.10.2014.
 */
public class CountryDTO {
    private Long id;
    private String country;
    public CountryDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
