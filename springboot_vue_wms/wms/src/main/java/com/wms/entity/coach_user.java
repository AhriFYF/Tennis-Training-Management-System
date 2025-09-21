package com.wms.entity;
import java.time.LocalDateTime;
import javax.persistence.*;

@Table(name="coach_user")
@Entity
public class coach_user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coach_user_id")
    private Integer coachUserId;

    @Column(name = "coach_name")
    private String coachName;

    @Column(name = "coach_gender")
    private String coachGender;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "coach_age")
    private String studentAge;

    @Column(name = "phone_number")
    private String parentsPhoneNumber;

    @Column(name = "campus")
    private String campus;

    @Column(name = "score")
    private String score;

    @Column(name = "photo")
    private String photo;

    //1初级 2中级 3高级
    @Column(name = "level")
    private Integer level;

    //0拒绝 1待审核 2审核通过
    @Column(name = "audit")
    private Integer audit;

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public Integer getCoachUserId() {
        return coachUserId;
    }

    public void setCoachUserId(Integer coachUserId) {
        this.coachUserId = coachUserId;
    }

    public String getCoachName() {
        return coachName;
    }

    public void setCoachName(String coachName) {
        this.coachName = coachName;
    }

    public String getCoachGender() {
        return coachGender;
    }

    public void setCoachGender(String coachGender) {
        this.coachGender = coachGender;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    @Override
    public String toString() {
        return "coach_user{" +
                "level=" + level +
                ", coachUserId=" + coachUserId +
                ", coachName='" + coachName + '\'' +
                ", coachGender='" + coachGender + '\'' +
                ", userId=" + userId +
                ", studentAge='" + studentAge + '\'' +
                ", parentsPhoneNumber='" + parentsPhoneNumber + '\'' +
                ", campus='" + campus + '\'' +
                ", score='" + score + '\'' +
                ", photo='" + photo + '\'' +
                ", audit=" + audit +
                '}';
    }
}
