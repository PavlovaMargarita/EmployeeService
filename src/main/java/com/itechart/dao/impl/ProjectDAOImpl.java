package com.itechart.dao.impl;

import com.itechart.dao.ProjectDAO;
import com.itechart.model.Project;
import org.springframework.stereotype.Repository;

/**
 * Created by Margarita on 17.10.2014.
 */
@Repository
public class ProjectDAOImpl extends Base implements ProjectDAO {

    @Override
    public void createProject(Project project) {
        sessionFactory.getCurrentSession().save(project);
    }
}
