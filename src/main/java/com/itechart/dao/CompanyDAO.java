package com.itechart.dao;

import com.itechart.model.Company;

import java.util.List;
/**
 * Created by Margarita on 16.10.2014.
 */
public interface CompanyDAO {
    public List readCompany();
    public void createCompany(Company company);
}
