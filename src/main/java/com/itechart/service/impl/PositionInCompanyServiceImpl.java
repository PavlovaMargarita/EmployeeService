package com.itechart.service.impl;

import com.itechart.dto.PositionInCompanyDTO;
import com.itechart.model.PositionInCompany;
import com.itechart.repository.PositionInCompanyRepository;
import com.itechart.service.PositionInCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Margarita on 20.10.2014.
 */
@Service
public class PositionInCompanyServiceImpl implements PositionInCompanyService {

    @Autowired
    private PositionInCompanyRepository positionInCompanyRepository;

    @Override
    public List<PositionInCompanyDTO> readPositionInCompanyList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String company = authority.get(1).getAuthority();
        Pageable topTen = new PageRequest(0, 10);
        List<PositionInCompany> positionInCompanyList = positionInCompanyRepository.readPositionInCompanyList(company, topTen);
        List<PositionInCompanyDTO> positionInCompanyDTOList = new ArrayList();
        for(PositionInCompany positionInCompany: positionInCompanyList){
            positionInCompanyDTOList.add(positionInCompanyToPositionInCompanyDTO(positionInCompany));
        }
        return positionInCompanyDTOList;
    }


    private PositionInCompanyDTO positionInCompanyToPositionInCompanyDTO(PositionInCompany positionInCompany){
        PositionInCompanyDTO positionInCompanyDTO = new PositionInCompanyDTO();
        positionInCompanyDTO.setId(positionInCompany.getId());
        positionInCompanyDTO.setPosition(positionInCompany.getPosition());
        return positionInCompanyDTO;
    }
}
