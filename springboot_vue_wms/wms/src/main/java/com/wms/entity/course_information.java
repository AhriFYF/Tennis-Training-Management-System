package com.system.tabletennis_training_management_system.pojo;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Table(name="course_information")
@Entity
public class course_information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_information_id")
    private Integer courseInformationId;

    @Column(name = "course_name")
    private String courseName;

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

    @Column(name = "course_images")
    private String courseImages;

    @Column(name = "course_introduction", columnDefinition = "LONGTEXT")
    private String courseIntroduction;

    @Column(name = "hits")
    private Integer hits;

    @Column(name = "praise_len")
    private Integer praiseLen;

    @Column(name = "collect_len")
    private Integer collectLen;

    @Column(name = "comment_len")
    private Integer commentLen;

    @Column(name = "student_course_selection_limit_times")
    private Integer studentCourseSelectionLimitTimes;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public Double getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(Double courseHours) {
        this.courseHours = courseHours;
    }

    public Integer getCourseInformationId() {
        return courseInformationId;
    }

    public void setCourseInformationId(Integer courseInformationId) {
        this.courseInformationId = courseInformationId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public String getDurationOfClassHours() {
        return durationOfClassHours;
    }

    public void setDurationOfClassHours(String durationOfClassHours) {
        this.durationOfClassHours = durationOfClassHours;
    }

    public Double getCoursePrices() {
        return coursePrices;
    }

    public void setCoursePrices(Double coursePrices) {
        this.coursePrices = coursePrices;
    }

    public String getCourseImages() {
        return courseImages;
    }

    public void setCourseImages(String courseImages) {
        this.courseImages = courseImages;
    }

    public String getCourseIntroduction() {
        return courseIntroduction;
    }

    public void setCourseIntroduction(String courseIntroduction) {
        this.courseIntroduction = courseIntroduction;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getPraiseLen() {
        return praiseLen;
    }

    public void setPraiseLen(Integer praiseLen) {
        this.praiseLen = praiseLen;
    }

    public Integer getCollectLen() {
        return collectLen;
    }

    public void setCollectLen(Integer collectLen) {
        this.collectLen = collectLen;
    }

    public Integer getCommentLen() {
        return commentLen;
    }

    public void setCommentLen(Integer commentLen) {
        this.commentLen = commentLen;
    }

    public Integer getStudentCourseSelectionLimitTimes() {
        return studentCourseSelectionLimitTimes;
    }

    public void setStudentCourseSelectionLimitTimes(Integer studentCourseSelectionLimitTimes) {
        this.studentCourseSelectionLimitTimes = studentCourseSelectionLimitTimes;
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
        return "course_information{" +
                "courseInformationId=" + courseInformationId +
                ", courseName='" + courseName + '\'' +
                ", courseTypes='" + courseTypes + '\'' +
                ", coachUser=" + coachUser +
                ", coachName='" + coachName + '\'' +
                ", courseHours=" + courseHours +
                ", durationOfClassHours='" + durationOfClassHours + '\'' +
                ", coursePrices=" + coursePrices +
                ", courseImages='" + courseImages + '\'' +
                ", courseIntroduction='" + courseIntroduction + '\'' +
                ", hits=" + hits +
                ", praiseLen=" + praiseLen +
                ", collectLen=" + collectLen +
                ", commentLen=" + commentLen +
                ", studentCourseSelectionLimitTimes=" + studentCourseSelectionLimitTimes +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
