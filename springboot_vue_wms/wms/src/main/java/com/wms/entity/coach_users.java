package com.wms.entity;

import javax.persistence.*;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@Entity
@Table(name = "coach_users")
@TableName("coach_users")
public class coach_users {

    @Id
    @TableId(value = "coach_id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coach_id")
    private Integer coachId;

    @Column(name = "coach_no", unique = true, nullable = false)
    private String coachNo; // 教练编号

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", length = 1)
    private String gender; // M-男, F-女

    @Column(name = "age")
    private Integer age;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "user_id", unique = true)
    private Integer userId; // 关联基础用户表

    @Column(name = "campus_id")
    private Integer campusId; // 所属校区

    @Column(name = "achievements", columnDefinition = "TEXT")
    private String achievements; // 比赛成绩

    @Column(name = "photo_url")
    private String photoUrl; // 照片路径

    @Column(name = "level")
    private Integer level; // 1-初级, 2-中级, 3-高级

    @Column(name = "audit_status")
    private Integer auditStatus = 0; // 0-待审核, 1-通过, 2-拒绝

    @Column(name = "hourly_rate")
    private Integer hourlyRate; // 课时费(元/小时)

    public Integer getCoachId() {
        return coachId;
    }

    @Override
    public String toString() {
        return "coach_users{" +
                "coachId=" + coachId +
                ", coachNo='" + coachNo + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", userId=" + userId +
                ", campusId=" + campusId +
                ", achievements='" + achievements + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                ", level=" + level +
                ", auditStatus=" + auditStatus +
                ", hourlyRate=" + hourlyRate +
                ", campusName='" + campusName + '\'' +
                '}';
    }

    public void setCoachId(Integer coachId) {
        this.coachId = coachId;
    }

    public String getCoachNo() {
        return coachNo;
    }

    public void setCoachNo(String coachNo) {
        this.coachNo = coachNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public void setHourlyRate(Integer hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    // 关联校区信息（非数据库字段）
    @Transient
    @TableField(exist = false)
    private String campusName;

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }

    // 自动计算课时费
    public Integer getHourlyRate() {
        if (this.level == null) {
            return 80; // default value
        }
        return switch(this.level) {
            case 1 -> 80;
            case 2 -> 150;
            case 3 -> 200;
            default -> 80;
        };
    }
}