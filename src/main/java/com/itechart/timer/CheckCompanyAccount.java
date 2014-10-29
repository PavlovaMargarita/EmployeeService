package com.itechart.timer;

import com.itechart.dto.CompanyDTO;
import com.itechart.projectValue.Params;
import com.itechart.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.*;
/**
 * Created by Margarita on 29.10.2014.
 */
public class CheckCompanyAccount {

    @Autowired
    private CompanyService companyService;

    @Scheduled(fixedRate = 24*60*60*1000)
    public void send() {
//        Date date = new Date();
//        List<Contact> contacts = contactDAO.searchContactByDateOfBirth(date.getMonth() + 1, date.getDate());
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom("sasha25121993@mail.ru");
//        simpleMailMessage.setSubject("Поздравление");
//        simpleMailMessage.setText("с Днём Рождения!");
//        for (Contact contact : contacts) {
//            if (contact.getEmail() != null) {
//                simpleMailMessage.setTo(contact.getEmail());
//                mailSender.send(simpleMailMessage);
//            }
//        }
        java.util.Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int currentDateDay = cal.get(Calendar.DAY_OF_MONTH);
        List<CompanyDTO> companyDTOList = companyService.readCompanyList();
        for(CompanyDTO companyDTO:companyDTOList){
            java.util.Date companyDate = new java.util.Date(companyDTO.getDateLastRefill().getTime());
            cal.setTime(companyDate);
            int companyDateDay = cal.get(Calendar.DAY_OF_MONTH);

            if(companyDateDay == currentDateDay){
                int sum= companyDTO.getAccountSum()- Params.COST_OF_USING_MONTH;
                companyDTO.setAccountSum(sum);
                companyService.updateCompany(companyDTO);
            }
}
        System.out.println("ok");


    }
}
