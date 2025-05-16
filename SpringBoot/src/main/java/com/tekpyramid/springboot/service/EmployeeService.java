package com.tekpyramid.springboot.service;

import com.tekpyramid.springboot.dto.EmployeeDTO;
import com.tekpyramid.springboot.entity.Employee;

import java.util.List;

public interface EmployeeService {

    String saveEmployee(EmployeeDTO employeeDTO);

    String updateEmployee(int employeeId,EmployeeDTO employeeDTO);


    void deleteEmployeeById(int id);

    EmployeeDTO getEmployeeById(int id);

    List<Employee> getAll();
}
