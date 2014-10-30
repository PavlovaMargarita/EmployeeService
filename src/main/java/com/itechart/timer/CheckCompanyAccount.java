package com.itechart.timer;

import com.itechart.dto.CompanyDTO;
import com.itechart.projectValue.Params;
import com.itechart.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Margarita on 29.10.2014.
 */
public class CheckCompanyAccount {

    @Autowired
    private CompanyService companyService;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void checkCompanyAccount() {
        java.util.Date currentDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int currentDateDay = cal.get(Calendar.DAY_OF_MONTH);
        List<CompanyDTO> companyDTOList = companyService.readCompanyList();
        for (CompanyDTO companyDTO : companyDTOList) {
            java.util.Date companyDate = new java.util.Date(companyDTO.getDateBoundaryRefill().getTime());
            cal.setTime(companyDate);
            int companyDateDay = cal.get(Calendar.DAY_OF_MONTH);
            if (companyDateDay == currentDateDay) {
                int sum = companyDTO.getAccountSum() - Params.COST_OF_USING_MONTH;
                if (sum >= 0) {
                    cal.setTime(currentDate);
                    cal.add(Calendar.MONTH, 1);
                    companyDTO.setAccountSum(sum);
                    companyDTO.setDateBoundaryRefill(new java.sql.Date((cal.getTime()).getTime()));
                    companyService.updateCompanyEveryDay(companyDTO);
                }
            }
        }


    }
}
