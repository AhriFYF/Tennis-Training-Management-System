package com.wms.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="student_course_selection")
@TableName("student_course_selection")
public class student_course_selection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_course_selection_id")
    @TableId(value = "student_course_selection_id", type = IdType.AUTO)
    private Integer studentCourseSelectionId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_number")
    private String courseNumber;

    @Column(name = "course_types")
    private String courseTypes;

    @Column(name = "coach_user")
    private Integer coachUser;

    @Column(name = "coach_name")
    private String coachName;

    @Column(name = "course_hours")
    private Double courseHours;

    @Column(name = "duration_of_class_hours")
    private String durationOfClassHours;

    @Column(name = "course_prices")
    private Double coursePrices;

    @Column(name = "student_users")
    private Integer studentUsers;

    @Column(name = "name")
    private String name;

    @Column(name = "student_age")
    private String studentAge;

    @Column(name = "parents_phone_number")
    private String parentsPhoneNumber;

    @Column(name = "pay_state")
    private String payState;

    @Column(name = "pay_type")
    private String payType;

    @Column(name = "coach_scheduling_limit_times")
    private Integer coachSchedulingLimitTimes;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "source_table")
    private String sourceTable;

    @Column(name = "source_id")
    private Integer sourceId;

    @Column(name = "source_user_id")
    private Integer sourceUserId;

    public String getDurationOfClassHours() {
        return durationOfClassHours;
    }

    public void setDurationOfClassHours(String durationOfClassHours) {
        this.durationOfClassHours = durationOfClassHours;
    }

    public Integer getStudentCourseSelectionId() {
        return studentCourseSelectionId;
    }

    public void setStudentCourseSelectionId(Integer studentCourseSelectionId) {
        this.studentCourseSelectionId = studentCourseSelectionId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseTypes() {
        return courseTypes;
    }

    public void setCourseTypes(String courseTypes) {
        this.courseTypes = courseTypes;
    }

    public Integer getCoachUser() {
        return coachUser;
    }

    public void setCoachUser(Integer coachUser) {
        this.coachUser = coachUser;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public Double getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(Double courseHours) {
        this.courseHours = courseHours;
    }

    public Double getCoursePrices() {
        return coursePrices;
    }

    public void setCoursePrices(Double coursePrices) {
        this.coursePrices = coursePrices;
    }

    public Integer getStudentUsers() {
        return studentUsers;
    }

    public void setStudentUsers(Integer studentUsers) {
        this.studentUsers = studentUsers;
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

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getCoachSchedulingLimitTimes() {
        return coachSchedulingLimitTimes;
    }

    public void setCoachSchedulingLimitTimes(Integer coachSchedulingLimitTimes) {
        this.coachSchedulingLimitTimes = coachSchedulingLimitTimes;
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

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(Integer sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    @Override
    public String toString() {
        return "student_course_selection{" +
                "studentCourseSelectionId=" + studentCourseSelectionId +
                ", courseName='" + courseName + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", courseTypes='" + courseTypes + '\'' +
                ", coachUser=" + coachUser +
                ", coachName='" + coachName + '\'' +
                ", courseHours=" + courseHours +
                ", durationOfClassHours='" + durationOfClassHours + '\'' +
                ", coursePrices=" + coursePrices +
                ", studentUsers=" + studentUsers +
                ", name='" + name + '\'' +
                ", studentAge='" + studentAge + '\'' +
                ", parentsPhoneNumber='" + parentsPhoneNumber + '\'' +
                ", payState='" + payState + '\'' +
                ", payType='" + payType + '\'' +
                ", coachSchedulingLimitTimes=" + coachSchedulingLimitTimes +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", sourceTable='" + sourceTable + '\'' +
                ", sourceId=" + sourceId +
                ", sourceUserId=" + sourceUserId +
                '}';
    }
}