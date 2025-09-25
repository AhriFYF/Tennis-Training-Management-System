package com.wms.entity;

import javax.persistence.*;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@Entity
@Table(name = "student_users")
@TableName("student_users")
public class student_users {

    @Id
    @TableId(value = "student_id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "student_no", unique = true, nullable = false)
    private String studentNo; // 学号

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

    @Column(name = "campus_id")
    private Integer campusId; // 所属校区

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "campus_name")
    private String campusName;

    @Column(name = "balance")
    private Integer balance;


    @Override
    public String toString() {
        return "student_users{" +
                "studentId=" + studentId +
                ", studentNo='" + studentNo + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", userId=" + userId +
                ", classGrade='" + classGrade + '\'' +
                ", trainingHours=" + trainingHours +
                ", paymentStatus=" + paymentStatus +
                ", campusId=" + campusId +
                ", photoUrl='" + photoUrl + '\'' +
                ", campusName='" + campusName + '\'' +
                ", balance=" + balance +
                '}';
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getClassGrade() {
        return classGrade;
    }

    public void setClassGrade(String classGrade) {
        this.classGrade = classGrade;
    }

    public Integer getTrainingHours() {
        return trainingHours;
    }

    public void setTrainingHours(Integer trainingHours) {
        this.trainingHours = trainingHours;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

}