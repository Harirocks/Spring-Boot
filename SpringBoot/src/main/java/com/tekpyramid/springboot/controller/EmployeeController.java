package com.tekpyramid.springboot.controller;

import com.tekpyramid.springboot.dto.AddressDTO;
import com.tekpyramid.springboot.dto.EmployeeDTO;
import com.tekpyramid.springboot.entity.Employee;
import com.tekpyramid.springboot.response.ApiResponse;
import com.tekpyramid.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/mock")
    public ResponseEntity<?> mockEmployee(){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setStreet("Arulappa street");
        addressDTO.setCity("Chennai");
        addressDTO.setState("Tamil Nadu");
        addressDTO.setCountry("India");

        employeeDTO.setEmployeeName("Hariharan.K");
        employeeDTO.setEmail("hari@gmail.com");
        employeeDTO.setDateOfBirth(LocalDate.of(1995,07,26));
        employeeDTO.setGender("Male");
        employeeDTO.setSalary(150000);
        employeeDTO.setMobile(8901234567L);
        employeeDTO.setAddress(addressDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(employeeDTO);
    }

    @PostMapping("save-employee")
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDTO employeeDTO){

        String id=employeeService.saveEmployee(employeeDTO);

        ApiResponse apiResponse=new ApiResponse();

        apiResponse.setHttpStatus(HttpStatus.ACCEPTED);
        apiResponse.setMessage("Employee saved successfully");
        apiResponse.setData(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(apiResponse);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable("id")int id, @RequestBody EmployeeDTO employeeDTO){
        String updatedId = employeeService.updateEmployee(id, employeeDTO);
        ApiResponse response = new ApiResponse();
        response.setMessage("Employee updated successfully");
        response.setHttpStatus(HttpStatus.OK);
        response.setData(updatedId);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id")int id){
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("id")int id){
        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employeeDTO);
    }
    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        List<Employee> all = employeeService.getAll();
        return ResponseEntity.ok(all);
    }
}
