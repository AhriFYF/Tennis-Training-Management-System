package com.wms.entity;
import java.util.Date;
import javax.persistence.*;

@Table(name="teaching_evaluation")
@Entity
public class teaching_evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teaching_evaluation_id")
    private Integer teachingEvaluationId;

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

    @Column(name = "review_video")
    private String reviewVideo;

    @Column(name = "evaluation_content", columnDefinition = "LONGTEXT")
    private String evaluationContent;

    @Column(name = "source_user_id")
    private Integer sourceUserId;

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public Integer getTeachingEvaluationId() {
        return teachingEvaluationId;
    }

    public void setTeachingEvaluationId(Integer teachingEvaluationId) {
        this.teachingEvaluationId = teachingEvaluationId;
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

    public String getReviewVideo() {
        return reviewVideo;
    }

    public void setReviewVideo(String reviewVideo) {
        this.reviewVideo = reviewVideo;
    }

    public String getEvaluationContent() {
        return evaluationContent;
    }

    public void setEvaluationContent(String evaluationContent) {
        this.evaluationContent = evaluationContent;
    }

    public Integer getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(Integer sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    @Override
    public String toString() {
        return "teaching_evaluation{" +
                "teachingEvaluationId=" + teachingEvaluationId +
                ", courseName='" + courseName + '\'' +
                ", courseNumber='" + courseNumber + '\'' +
                ", coachUser=" + coachUser +
                ", coachName='" + coachName + '\'' +
                ", studentUsers=" + studentUsers +
                ", name='" + name + '\'' +
                ", scheduleDate=" + scheduleDate +
                ", schedulingPeriod='" + schedulingPeriod + '\'' +
                ", contentOfCourses='" + contentOfCourses + '\'' +
                ", reviewVideo='" + reviewVideo + '\'' +
                ", evaluationContent='" + evaluationContent + '\'' +
                ", sourceUserId=" + sourceUserId +
                '}';
    }
}
