package com.itechart.dao.impl;

import com.itechart.dao.PositionInCompanyDAO;
import com.itechart.model.PositionInCompany;
import org.springframework.stereotype.Repository;

/**
 * Created by Margarita on 17.10.2014.
 */
@Repository
public class PositionInCompanyDAOImpl extends Base implements PositionInCompanyDAO {
    @Override
    public void createPosition(PositionInCompany positionInCompany) {
        sessionFactory.getCurrentSession().save(positionInCompany);
    }
}
