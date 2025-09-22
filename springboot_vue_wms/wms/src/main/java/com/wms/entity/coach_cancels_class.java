package com.wms.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="coach_cancels_class")
@TableName("coach_cancels_class")
public class coach_cancels_class {
    @Id
    @TableId(value = "coach_cancels_class_id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coach_cancels_class_id")
    private Integer coachCancelsClassId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_number")
    private String courseNumber;

    @Column(name = "coach_user")
    private Integer coachUser;

    @Column(name = "coach_name")
    private String coachName;

    @Column(name = "student_users")
    private Integer studentUsers;

    @Column(name = "name")
    private String name;

    @Column(name = "schedule_date")
    private Date scheduleDate;

    @Column(name = "scheduling_period")
    private String schedulingPeriod;

    @Column(name = "content_of_courses", columnDefinition = "TEXT")
    private String contentOfCourses;

    @Column(name = "duration_of_teaching_hours")
    private Double durationOfTeachingHours;

    @Column(name = "course_notes")
    private String courseNotes;

    @Column(name = "source_user_id")
    private Integer sourceUserId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCoachCancelsClassId() {
        return coachCancelsClassId;
    }

    public void setCoachCancelsClassId(Integer coachCancelsClassId) {
        this.coachCancelsClassId = coachCancelsClassId;
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

    public Integer getStudentUsers() {
        return studentUsers;
    }

    public void setStudentUsers(Integer studentUsers) {
        this.studentUsers = studentUsers;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getSchedulingPeriod() {
        return schedulingPeriod;
    }

    public void setSchedulingPeriod(String schedulingPeriod) {
        this.schedulingPeriod = schedulingPeriod;
    }

    public String getContentOfCourses() {
        return contentOfCourses;
    }

    public void setContentOfCourses(String contentOfCourses) {
        this.contentOfCourses = contentOfCourses;
    }

    public Double getDurationOfTeachingHours() {
        return durationOfTeachingHours;
    }

    public void setDurationOfTeachingHours(Double durationOfTeachingHours) {
        this.durationOfTeachingHours = durationOfTeachingHours;
    }

    public String getCourseNotes() {
        return courseNotes;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public Integer getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(Integer sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    @Override
    public String toString() {
        return "coach_cancels_class{" +
                "scheduleDate=" + scheduleDate +
                ", coachCancelsClassId=" + coachCancelsClassId +
                ", courseName='" + courseName + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", coachUser=" + coachUser +
                ", coachName='" + coachName + '\'' +
                ", studentUsers=" + studentUsers +
                ", name='" + name + '\'' +
                ", schedulingPeriod='" + schedulingPeriod + '\'' +
                ", contentOfCourses='" + contentOfCourses + '\'' +
                ", durationOfTeachingHours=" + durationOfTeachingHours +
                ", courseNotes='" + courseNotes + '\'' +
                ", sourceUserId=" + sourceUserId +
                '}';
    }
}
