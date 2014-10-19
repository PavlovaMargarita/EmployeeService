package com.itechart.dao.impl;

import com.itechart.dao.EmployeeDAO;
import com.itechart.model.Employee;
import org.springframework.stereotype.Repository;

/**
 * Created by Margarita on 17.10.2014.
 */
@Repository
public class EmployeeDAOImpl extends Base implements EmployeeDAO {

    @Override
    public void createEmployee(Employee employee) {
        sessionFactory.getCurrentSession().save(employee);
    }
}
