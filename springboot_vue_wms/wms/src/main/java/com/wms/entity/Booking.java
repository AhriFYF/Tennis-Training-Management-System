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
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "coach_id", nullable = false)
    private Integer coachId;

    @Column(name = "coach_name", nullable = false, length = 50)
    private String coachName;

    @Column(name = "course_start_time", nullable = false)
    private LocalDateTime courseStartTime;

    @Column(name = "course_end_time", nullable = false)
    private LocalDateTime courseEndTime;

    @Column(name = "court_number", nullable = false, length = 20)
    private String courtNumber;

    @Column(name = "course_prices", nullable = false)
    private Double coursePrices;

    @Column(name = "status", nullable = false, length = 20)
    private String status; // 待确认、已确认、已取消

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

    @Column(name = "cancellation_reason", length = 200)
    private String cancellationReason;

    @Column(name = "cancelled_by", length = 20)
    private String cancelledBy; // student或coach

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public LocalDateTime getCourseStartTime() {
        return courseStartTime;
    }

    public void setCourseStartTime(LocalDateTime courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    public LocalDateTime getCourseEndTime() {
        return courseEndTime;
    }

    public void setCourseEndTime(LocalDateTime courseEndTime) {
        this.courseEndTime = courseEndTime;
    }

    public String getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(String courtNumber) {
        this.courtNumber = courtNumber;
    }

    public Double getCoursePrices() {
        return coursePrices;
    }

    public void setCoursePrices(Double coursePrices) {
        this.coursePrices = coursePrices;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }
}