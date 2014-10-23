package com.itechart.repository;

import com.itechart.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Margarita on 19.10.2014.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u  WHERE u.login = :login")
    public User readUser(@Param("login") String login);

    @Query("select u from User u")
    public List<User> readUserList(Pageable pageable);
}
