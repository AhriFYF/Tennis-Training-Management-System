package com.wms.dto;

import lombok.Data;

@Data
public class StudentDetailDTO {
    // student_users表字段
    private Integer studentId;
    private String name;
    private String gender;
    private Integer age;
    private String phone;
    private Integer userId;
    private String classGrade;
    private Integer trainingHours;
    private Integer paymentStatus;

    // 从user表关联查询的字段
    private String no;
    private Integer campusId;
    private String campusName;
}