package com.tekpyramid.springboot.dto;


import lombok.Data;


import java.time.LocalDate;

@Data
public class EmployeeDTO {
    private String employeeName;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private double salary;
    private long mobile;
    private AddressDTO address;

}
