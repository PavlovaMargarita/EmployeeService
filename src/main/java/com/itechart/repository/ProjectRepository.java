package com.itechart.repository;

import com.itechart.model.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Margarita on 19.10.2014.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("select p from Project p")
    public List<Project> readProjectList(Pageable pageable);
}
