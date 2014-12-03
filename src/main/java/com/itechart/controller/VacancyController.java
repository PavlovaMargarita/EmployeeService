package com.itechart.controller;

import com.itechart.model.mongo.Vacancy;
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
    public List<Vacancy> vacancyList(@RequestParam("currentPage") int currentPage, @RequestParam("pageRecords") int pageRecords) throws UnknownHostException {
        Logger.getLogger(VacancyController.class).info("Request: /EmployeeService/vacancy/vacancyList ");
        return vacancyService.readVacancyList(currentPage, pageRecords);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vacancyCount")
    @ResponseBody
    public long positionCount() throws UnknownHostException {
        Logger.getLogger(VacancyController.class).info("Request: /EmployeeService/vacancy/vacancyCount ");
        return vacancyService.vacancyCount();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveVacancyCreate")
    @ResponseBody
    public void saveVacancyCreate(@RequestBody Vacancy vacancy) throws UnknownHostException {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/vacancy/saveVacancyCreate");
        vacancyService.createVacancy(vacancy);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveVacancyUpdate")
    @ResponseBody
    public void saveVacancyUpdate(@RequestBody Vacancy vacancy) throws UnknownHostException {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/vacancy/saveVacancyUpdate");
        vacancyService.updateVacancy(vacancy);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/readVacancyById")
    @ResponseBody
    public Vacancy readVacancyById(@RequestParam("id") Long id) throws UnknownHostException {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/vacancy/vacancyById");
        return  vacancyService.readVacancyById(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deleteVacancyById")
    @ResponseBody
    public void deleteVacancyById(@RequestBody Vacancy vacancy) throws UnknownHostException {
        Logger.getLogger(EmployeeController.class).info("Request: /EmployeeService/vacancy/vacancyById");
        vacancyService.deleteVacancyById(vacancy.getId());
    }
}
