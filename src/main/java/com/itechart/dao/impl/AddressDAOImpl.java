package com.itechart.dao.impl;

import com.itechart.dao.AddressDAO;
import com.itechart.model.Address;
import org.springframework.stereotype.Repository;

/**
 * Created by Margarita on 17.10.2014.
 */
@Repository
public class AddressDAOImpl extends Base implements AddressDAO{

    @Override
    public void createAddress(Address address) {
        sessionFactory.getCurrentSession().save(address);
    }
}
