package com.itechart.dao.impl;

import com.itechart.dao.ProjectDAO;
import com.itechart.model.Project;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Margarita on 17.10.2014.
 */
@Service
public class ProjectDAOImpl implements ProjectDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createProject(Project project) {
        sessionFactory.getCurrentSession().save(project);
    }
}
