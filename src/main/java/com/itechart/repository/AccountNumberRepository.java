package com.itechart.repository;

import com.itechart.model.hibernate.AccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountNumberRepository extends JpaRepository<AccountNumber, Long> {
    @Query("select a from AccountNumber a where a.number=:number")
    public AccountNumber readAccountNumber(@Param("number")String number);
}
