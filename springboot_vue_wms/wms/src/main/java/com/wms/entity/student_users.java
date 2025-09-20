package com.wms.entity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name="student_users")
@Entity
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

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public String getParentsPhoneNumber() {
        return parentsPhoneNumber;
    }

    public void setParentsPhoneNumber(String parentsPhoneNumber) {
        this.parentsPhoneNumber = parentsPhoneNumber;
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

    public String getStudentGender() {
        return studentGender;
    }

    public void setStudentGender(String studentGender) {
        this.studentGender = studentGender;
    }

    public String getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(String studentAge) {
        this.studentAge = studentAge;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
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
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
