package com.system.tabletennis_training_management_system.pojo;
import java.time.LocalDateTime;
import jakarta.persistence.*;

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

    @Column(name = "examine_state")
    private String examineState;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
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

    public String getExamineState() {
        return examineState;
    }

    public void setExamineState(String examineState) {
        this.examineState = examineState;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "User{" +
                "coachUserId=" + coachUserId +
                ", coachName='" + coachName + '\'' +
                ", coachGender='" + coachGender + '\'' +
                ", examineState='" + examineState + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
