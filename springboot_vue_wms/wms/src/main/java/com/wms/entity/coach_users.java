package com.wms.entity;

import javax.persistence.*;

import com.baomidou.mybatisplus.annotation.IdType;
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

    // 自动计算课时费
    public Integer getHourlyRate() {
        return switch(this.level) {
            case 1 -> 80;
            case 2 -> 150;
            case 3 -> 200;
            default -> 80;
        };
    }

    // 关联校区信息（非数据库字段）
    @Transient
    private String campusName;
}