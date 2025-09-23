package com.wms.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="course_information")
@TableName("course_information")
public class course_information {
    @Id
    @TableId(value = "course_information_id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_information_id")
    private Integer courseInformationId;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "course_types")
    private String courseTypes;

    @Column(name = "coach_user")//此处为上传课程的老师的coach_users中的coach_id
    private Integer coachUser;

    @Column(name = "coach_name")
    private String coachName;

    @Column(name = "course_hours")//课程时长
    private Double courseHours;


    @Column(name = "course_prices")
    private Double coursePrices;

    @Column(name = "course_images")
    private String courseImages;

    @Column(name = "course_introduction", columnDefinition = "LONGTEXT")
    private String courseIntroduction;

    @Column(name = "hits")//点击量，默认为0
    private Integer hits;

    @Column(name = "collect_len")//收藏数，同上
    private Integer collectLen;

    @Column(name = "comment_len")//评论数，同上上
    private Integer commentLen;

    @Column(name = "student_course_selection_limit_times")//学生最晚选课的截止时间，既课程开始时间即可
    private Integer studentCourseSelectionLimitTimes;

    @Column(name = "status")
    private String status;//0已上传上课时间但没人预约 1有人预约等待教练确认 2确定有预约

    @Column(name = "course_start_time")
    private LocalDateTime courseStartTime;

    @Column(name = "course_end_time")
    private LocalDateTime courseEndTime;

    @Column(name = "campus_id")
    private Integer campusId;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }

    @Override
    public String toString() {
        return "course_information{" +
                "coursePrices=" + coursePrices +
                ", courseInformationId=" + courseInformationId +
                ", courseName='" + courseName + '\'' +
                ", courseTypes='" + courseTypes + '\'' +
                ", coachUser=" + coachUser +
                ", coachName='" + coachName + '\'' +
                ", courseHours=" + courseHours +
                ", courseImages='" + courseImages + '\'' +
                ", courseIntroduction='" + courseIntroduction + '\'' +
                ", hits=" + hits +
                ", collectLen=" + collectLen +
                ", commentLen=" + commentLen +
                ", studentCourseSelectionLimitTimes=" + studentCourseSelectionLimitTimes +
                ", status='" + status + '\'' +
                ", courseStartTime=" + courseStartTime +
                ", courseEndTime=" + courseEndTime +
                ", campusId=" + campusId +
                '}';
    }
}
