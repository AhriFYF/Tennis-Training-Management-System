package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@TableName("bookings")
public class Booking {
    @Id
    @TableId(value = "booking_id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "coach_id", nullable = false)
    private Integer coachId;

    @Column(name = "course_name", nullable = false, length = 100)
    private String courseName;

    @Column(name = "course_number", nullable = false, length = 50)
    private String courseNumber;

    @Column(name = "schedule_date", nullable = false)
    private LocalDateTime scheduleDate;

    @Column(name = "scheduling_period", nullable = false, length = 50)
    private String schedulingPeriod;

    @Column(name = "status", nullable = false)
    private Integer status; // 0-待确认, 1-已确认, 2-已取消, 3-已完成

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

    @Column(name = "campus_id", nullable = false)
    private Integer campusId;

    @Column(name = "notes", length = 500)
    private String notes;

    // 构造函数
    public Booking() {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", studentId=" + studentId +
                ", coachId=" + coachId +
                ", courseName='" + courseName + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", scheduleDate=" + scheduleDate +
                ", schedulingPeriod='" + schedulingPeriod + '\'' +
                ", status=" + status +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", campusId=" + campusId +
                ", notes='" + notes + '\'' +
                '}';
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getCoachId() {
        return coachId;
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
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

    public LocalDateTime getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(LocalDateTime scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getSchedulingPeriod() {
        return schedulingPeriod;
    }

    public void setSchedulingPeriod(String schedulingPeriod) {
        this.schedulingPeriod = schedulingPeriod;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // 新增：状态检查方法
    public boolean isPending() {
        return this.status == 0;
    }

    public boolean isConfirmed() {
        return this.status == 1;
    }

    public boolean isCancelled() {
        return this.status == 2;
    }

    public boolean isCompleted() {
        return this.status == 3;
    }
}