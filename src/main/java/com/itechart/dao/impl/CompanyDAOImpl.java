package com.itechart.dao.impl;

import com.itechart.dao.CompanyDAO;
import com.itechart.model.Company;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Margarita on 16.10.2014.
 */

@Repository
public class CompanyDAOImpl extends Base implements CompanyDAO {

    @Override
    public List readCompany() {
        return null;
    }

    @Override
    public void createCompany(Company company) {
        sessionFactory.getCurrentSession().save(company);
    }
}
