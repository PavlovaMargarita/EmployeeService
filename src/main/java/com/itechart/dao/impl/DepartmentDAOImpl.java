package com.itechart.dao.impl;

import com.itechart.dao.DepartmentDAO;
import com.itechart.model.Department;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Margarita on 16.10.2014.
 */
@Service
public class DepartmentDAOImpl implements DepartmentDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createDepartment(Department department) {
        sessionFactory.getCurrentSession().save(department);
    }
}
