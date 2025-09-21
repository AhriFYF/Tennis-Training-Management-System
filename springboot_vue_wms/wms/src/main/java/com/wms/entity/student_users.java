package com.wms.entity;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="student_users")
@TableName("student_users")
public class student_users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_users_id")
    private Integer studentUsersId;

    @Column(name = "name")
    private String name;

    @Column(name = "student_gender")
    private String studentGender;

    @Column(name = "student_age")
    private String studentAge;

    @Column(name = "parents_phone_number")
    private String parentsPhoneNumber;

    @Column(name = "user_id")
    private Integer userId;

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public Integer getStudentUsersId() {
        return studentUsersId;
    }

    public void setStudentUsersId(Integer studentUsersId) {
        this.studentUsersId = studentUsersId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(String studentAge) {
        this.studentAge = studentAge;
    }

    public String getParentsPhoneNumber() {
        return parentsPhoneNumber;
    }

    public void setParentsPhoneNumber(String parentsPhoneNumber) {
        this.parentsPhoneNumber = parentsPhoneNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "student_users{" +
                "studentUsersId=" + studentUsersId +
                ", name='" + name + '\'' +
                ", studentGender='" + studentGender + '\'' +
                ", studentAge='" + studentAge + '\'' +
                ", parentsPhoneNumber='" + parentsPhoneNumber + '\'' +
                ", userId=" + userId +
                '}';
    }
}
