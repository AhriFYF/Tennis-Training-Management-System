package com.system.tabletennis_training_management_system.pojo;
import jakarta.persistence.*;
import java.util.Date;

@Table(name="coach_scheduling")
@Entity
public class coach_scheduling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coach_scheduling_id")
    private Integer coachSchedulingId;

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

    @Column(name = "source_id")
    private Integer sourceId;

    @Column(name = "source_user_id")
    private Integer sourceUserId;

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public Integer getCoachSchedulingId() {
        return coachSchedulingId;
    }

    public void setCoachSchedulingId(Integer coachSchedulingId) {
        this.coachSchedulingId = coachSchedulingId;
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

    @java.lang.Override
    public java.lang.String toString() {
        return "coach_scheduling{" +
                "schedulingPeriod='" + schedulingPeriod + '\'' +
                ", coachSchedulingId=" + coachSchedulingId +
                ", courseName='" + courseName + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", coachUser=" + coachUser +
                ", coachName='" + coachName + '\'' +
                ", studentUsers=" + studentUsers +
                ", name='" + name + '\'' +
                ", scheduleDate=" + scheduleDate +
                ", contentOfCourses='" + contentOfCourses + '\'' +
                ", sourceId=" + sourceId +
                ", sourceUserId=" + sourceUserId +
                '}';
    }
}
