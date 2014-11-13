package com.itechart.repository;

import com.itechart.model.AccountNumber;
import com.itechart.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by marharyta.pavlova on 13.11.2014.
 */
public interface AccountNumberRepository extends JpaRepository<AccountNumber, Long> {
    @Query("select a from AccountNumber a where a.number=:number")
    public AccountNumber readAccountNumber(@Param("number")String number);
}
