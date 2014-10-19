package com.itechart.dao.impl;

import com.itechart.dao.DepartmentDAO;
import com.itechart.model.Department;
import org.springframework.stereotype.Repository;

/**
 * Created by Margarita on 16.10.2014.
 */
@Repository
public class DepartmentDAOImpl extends Base implements DepartmentDAO {

    @Override
    public void createDepartment(Department department) {
        sessionFactory.getCurrentSession().save(department);
    }
}
