package com.itechart.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by Margarita on 19.10.2014.
 */
@Repository
public class Base {

    @Autowired
    SessionFactory sessionFactory;

}
