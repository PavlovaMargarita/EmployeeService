package com.itechart.controller;

import com.itechart.model.Vacancy;
import com.itechart.service.VacancyService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;

@Controller
@RequestMapping("/vacancy")
public class VacancyController {
    @Autowired
    private VacancyService vacancyService;

    @RequestMapping(method = RequestMethod.GET, value = "/vacancyList")
    @ResponseBody
    public List<Vacancy> vacancyList(@RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords) {
        Logger.getLogger(VacancyController.class).info("Request: /EmployeeService/vacancy/vacancyList ");
        List <Vacancy> vacancyList;
        try {
            vacancyList = vacancyService.readVacancyList(currentPage, pageRecords);
        } catch (UnknownHostException e) {
            Logger.getLogger(VacancyController.class).info("Mongo unknown host exception");
            throw new com.itechart.exception.UnknownHostException();
        }
        return vacancyList;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vacancyCount")
    @ResponseBody
    public long positionCount() {
        Logger.getLogger(VacancyController.class).info("Request: /EmployeeService/vacancy/vacancyCount ");
        long count;
        try {
            count = vacancyService.vacancyCount();
        } catch (UnknownHostException e) {
            Logger.getLogger(VacancyController.class).info("Mongo unknown host exception");
            throw new com.itechart.exception.UnknownHostException();
        }
        return count;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveVacancyCreate")
    @ResponseBody
    public void saveVacancyCreate(@RequestBody Vacancy vacancy){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/vacancy/saveVacancyCreate");
        try {
            vacancyService.createVacancy(vacancy);
        } catch (UnknownHostException e) {
            Logger.getLogger(EmployeeController.class).error("Mongo unknown host exception");
            throw new com.itechart.exception.UnknownHostException();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveVacancyUpdate")
    @ResponseBody
    public void saveVacancyUpdate(@RequestBody Vacancy vacancy){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/vacancy/saveVacancyUpdate");
        try {
            vacancyService.updateVacancy(vacancy);
        } catch (UnknownHostException e) {
            Logger.getLogger(EmployeeController.class).error("Mongo unknown host exception");
            throw new com.itechart.exception.UnknownHostException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/readVacancyById")
    @ResponseBody
    public Vacancy readVacancyById(@RequestParam("id") Long id){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/vacancy/vacancyById");
        Vacancy vacancy;
        try {
            vacancy = vacancyService.readVacancyById(id);
        } catch (UnknownHostException e) {
            Logger.getLogger(EmployeeController.class).error("Mongo unknown host exception");
            throw new com.itechart.exception.UnknownHostException();
        }
        return vacancy;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteVacancyById")
    @ResponseBody
    public void deleteVacancyById(@RequestBody Vacancy vacancy){
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/vacancy/vacancyById");
        try {
            vacancyService.deleteVacancyById(vacancy.getId());
        } catch (UnknownHostException e) {
            Logger.getLogger(EmployeeController.class).error("Mongo unknown host exception");
            throw new com.itechart.exception.UnknownHostException();
        }
    }
}
