package com.itechart.service;

import com.itechart.dto.AddressDTO;
import com.itechart.dto.DepartmentDTO;

import java.util.List;
/**
 * Created by Margarita on 20.10.2014.
 */
public interface DepartmentService {
    public List<DepartmentDTO> readDepartmentList();
    public List<AddressDTO> readAddressList(Long departmentId);
}
