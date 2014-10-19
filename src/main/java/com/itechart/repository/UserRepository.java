package com.itechart.repository;

import com.itechart.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Margarita on 19.10.2014.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
