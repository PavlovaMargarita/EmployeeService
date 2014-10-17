package com.itechart.dao.impl;

import com.itechart.dao.PositionInCompanyDAO;
import com.itechart.model.PositionInCompany;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Margarita on 17.10.2014.
 */
@Service
public class PosotionInCompanyDAOImpl implements PositionInCompanyDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createPosition(PositionInCompany positionInCompany) {
        sessionFactory.getCurrentSession().save(positionInCompany);
    }
}
