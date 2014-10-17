package com.itechart.dao.impl;

import com.itechart.dao.CompanyDAO;
import com.itechart.model.Company;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Margarita on 16.10.2014.
 */

@Component
public class CompanyDAOImpl implements CompanyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List readCompany() {
        return null;
    }

    @Override
    public void createCompany(Company company) {
        sessionFactory.getCurrentSession().save(company);
    }
}
