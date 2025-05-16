package com.tekpyramid.springboot.service;

import com.tekpyramid.springboot.dto.AddressDTO;
import com.tekpyramid.springboot.dto.EmployeeDTO;
import com.tekpyramid.springboot.entity.Address;
import com.tekpyramid.springboot.entity.Employee;
import com.tekpyramid.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public String saveEmployee(EmployeeDTO employeeDTO) {
        Address address = Address.builder()
                .street(employeeDTO.getAddress().getStreet())
                .city(employeeDTO.getAddress().getCity())
                .state(employeeDTO.getAddress().getState())
                .country(employeeDTO.getAddress().getCountry())
                .build();

        // Build Employee from DTO and assign address
        Employee employee = Employee.builder()
                .employeeName(employeeDTO.getEmployeeName())
                .email(employeeDTO.getEmail())
                .dateOfBirth(employeeDTO.getDateOfBirth())
                .gender(employeeDTO.getGender())
                .mobile(employeeDTO.getMobile())
                .salary(employeeDTO.getSalary())
                .address(address)
                .build();

        // Set the reverse relationship
        address.setEmployee(employee);

        // Save employee (address will be saved due to cascade)
        Employee savedEmployee = employeeRepository.save(employee);

        // Return saved employee ID as string
        return savedEmployee.getEmployeeId() + "";
    }

    @Override
    public String updateEmployee(int employeeId,EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(()->new RuntimeException("Employee not found"));

        // Update basic employee fields
        existingEmployee.setEmployeeName(employeeDTO.getEmployeeName());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setDateOfBirth(employeeDTO.getDateOfBirth());
        existingEmployee.setGender(employeeDTO.getGender());
        existingEmployee.setSalary(employeeDTO.getSalary());
        existingEmployee.setMobile(employeeDTO.getMobile());

        // Update address
        Address address = existingEmployee.getAddress();
        address.setStreet(employeeDTO.getAddress().getStreet());
        address.setCity(employeeDTO.getAddress().getCity());
        address.setState(employeeDTO.getAddress().getState());
        address.setCountry(employeeDTO.getAddress().getCountry());

        address.setEmployee(existingEmployee); // Maintain relation

        existingEmployee.setAddress(address);

        Employee updated = employeeRepository.save(existingEmployee);
        return updated.getEmployeeId() + "";
    }

    @Override
    public void deleteEmployeeById(int id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDTO getEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        // Map entity to DTO (manually or using ModelMapper)
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmployeeName(employee.getEmployeeName());
        dto.setEmail(employee.getEmail());
        dto.setGender(employee.getGender());
        dto.setSalary(employee.getSalary());
        dto.setMobile(employee.getMobile());
        dto.setDateOfBirth(employee.getDateOfBirth());

        AddressDTO addressDto = new AddressDTO();
        addressDto.setStreet(employee.getAddress().getStreet());
        addressDto.setCity(employee.getAddress().getCity());
        addressDto.setState(employee.getAddress().getState());
        addressDto.setCountry(employee.getAddress().getCountry());


        dto.setAddress(addressDto);

        return dto;
    }

    @Override
    public List<Employee> getAll() {
        return  employeeRepository.findAll() ;
    }


}
