package com.itechart.dao.impl;

import com.itechart.dao.UserDAO;
import com.itechart.model.User;
import org.springframework.stereotype.Repository;

/**
 * Created by Margarita on 17.10.2014.
 */
@Repository
public class UserDAOImpl extends Base implements UserDAO {

    @Override
    public void createUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }
}
