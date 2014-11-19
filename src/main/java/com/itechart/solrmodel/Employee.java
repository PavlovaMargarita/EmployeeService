package com.itechart.solrmodel;

import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by marharyta.pavlova on 18.11.2014.
 */

public class Employee{

    @Field(value="id")
    private String id;

    @Field(value="f_name")
    private String f_name;

    @Field(value = "s_name")
    private String s_name;

    @Field(value="email")
    private String email;

    public Employee(){}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}