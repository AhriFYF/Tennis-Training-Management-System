package com.wms.dto;

import lombok.Data;

@Data
public class StudentRegisterDTO {
    private String no;
    private String password;
    private String name;
    private String phone;
    private Integer age;
    private String gender; // M-男, F-女
    private String classGrade;
    private Integer trainingHours = 0;
    private Integer paymentStatus = 0;
    private Integer campusId;
}