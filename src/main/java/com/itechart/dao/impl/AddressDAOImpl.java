package com.itechart.dao.impl;

import com.itechart.dao.AddressDAO;
import com.itechart.model.Address;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Margarita on 17.10.2014.
 */
@Service
public class AddressDAOImpl implements AddressDAO{
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createAddress(Address address) {
        sessionFactory.getCurrentSession().save(address);
    }
}
