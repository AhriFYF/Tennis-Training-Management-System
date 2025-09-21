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
}
