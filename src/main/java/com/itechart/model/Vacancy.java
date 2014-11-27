package com.itechart.model;

import org.springframework.data.annotation.Id;

import javax.persistence.IdClass;
import java.sql.Date;

/**
 * Created by marharyta.pavlova on 24.11.2014.
 */
public class Vacancy {
    private long id;
    private String description;
    private Date dateClose;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateClose() {
        return dateClose;
    }

    public void setDateClose(Date dateClose) {
        this.dateClose = dateClose;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", dateClose=" + dateClose +
                '}';
    }
}
