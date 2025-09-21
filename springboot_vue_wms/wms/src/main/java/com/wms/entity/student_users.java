package com.wms.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student_users")
public class student_users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", length = 1)
    private String gender; // M-男, F-女

    @Column(name = "age")
    private Integer age;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "user_id", unique = true)
    private Integer userId; // 关联基础用户表

    @Column(name = "class_grade")
    private String classGrade; // 班级信息

    @Column(name = "training_hours")
    private Integer trainingHours = 0; // 训练时长(小时)

    @Column(name = "payment_status")
    private Integer paymentStatus = 0; // 0-未缴费, 1-已缴费

    // 关联校区信息（非数据库字段）
    @Transient
    private String campusName;
}