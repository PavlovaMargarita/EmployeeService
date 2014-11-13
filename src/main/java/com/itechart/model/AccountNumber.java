package com.itechart.model;

import javax.persistence.*;

/**
 * Created by marharyta.pavlova on 13.11.2014.
 */
@Entity
@Table(name = "account_number")
public class AccountNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "INT unsigned")
    private Long id;

    @Column(nullable = false)
    private String number;

    public AccountNumber(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
