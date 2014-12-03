package com.itechart.service;

import com.itechart.model.mongo.Vacancy;

import java.net.UnknownHostException;
import java.util.List;

public interface VacancyService {
    public List<Vacancy> readVacancyList(int pageNumber, int pageRecords) throws UnknownHostException;
    public void createVacancy(Vacancy vacancy) throws UnknownHostException;
    public void updateVacancy(Vacancy vacancy) throws UnknownHostException;
    public Vacancy readVacancyById(long id) throws UnknownHostException;
    public void deleteVacancyById(long id) throws UnknownHostException;
    public long vacancyCount() throws UnknownHostException;
}
