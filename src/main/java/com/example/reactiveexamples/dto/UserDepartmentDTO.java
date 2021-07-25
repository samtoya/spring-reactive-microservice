package com.example.reactiveexamples.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDepartmentDTO {
    private Integer userId;
    private String userName;
    private int age;
    private double salary;
    private Integer departmentId;
    private String departmentName;
    private String loc;
}
