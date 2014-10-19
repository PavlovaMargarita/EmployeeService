package com.itechart;

import com.itechart.model.Country;
import com.itechart.service.CompanyService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Margarita on 16.10.2014.
 */
public class Main {
    public static void main(String[] args) {

        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("spring.config.xml");
        CompanyService companyService =
                (CompanyService) ctx.getBean("companyServiceImpl");

        Country country = new Country();
        country.setCountry("test");
        companyService.createCountry(country);
    }
}
