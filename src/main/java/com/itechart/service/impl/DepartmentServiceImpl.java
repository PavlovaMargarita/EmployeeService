package com.itechart.service.impl;

import com.itechart.dto.DepartmentDTO;
import com.itechart.model.Department;
import com.itechart.repository.DepartmentRepository;
import com.itechart.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Margarita on 20.10.2014.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List readDepartmentList() {
        List <Department> departmentList = departmentRepository.findAll();
        List departmentDTOList = new ArrayList();
        for(Department department: departmentList){
            departmentDTOList.add(departmentToDepartmentDTO(department));
        }
        return departmentDTOList;
    }

    private DepartmentDTO departmentToDepartmentDTO(Department department){
        DepartmentDTO departmentDTO = new DepartmentDTO();
        if(department != null) {
            departmentDTO.setId(department.getId());
            departmentDTO.setDepartment(department.getDepartmentName());
        }
        return departmentDTO;
    }
}
