package com.itechart.service.impl;

import com.itechart.dto.SexDTO;
import com.itechart.enumProperty.SexEnum;
import com.itechart.service.SexService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Margarita on 21.10.2014.
 */
@Service
public class SexServiceImpl implements SexService {
    @Override
    public List<SexDTO> readSexEnum() {
        List<SexDTO> sexEnum = new ArrayList();
        for(int i = 0; i < SexEnum.values().length; i++) {
            SexDTO sexDTO = new SexDTO();
            sexDTO.setSexEnum(SexEnum.values()[i]);
            if(sexDTO.getSexEnum().equals(SexEnum.FEMALE)){
                sexDTO.setRoleRussian("Женский");
            }
            if(sexDTO.getSexEnum().equals(SexEnum.MALE)){
                sexDTO.setRoleRussian("Мужской");
            }
            sexEnum.add(sexDTO);
        }
        return sexEnum;
    }
}
