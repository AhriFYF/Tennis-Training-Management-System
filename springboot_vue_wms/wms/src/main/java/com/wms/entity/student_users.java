package com.wms.entity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name="student_users")
@Entity
public class student_users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_users_id")
    private Integer studentUsersId;

    @Column(name = "name")
    private String name;

    @Column(name = "student_gender")
    private String studentGender;

    @Column(name = "student_age")
    private String studentAge;

    @Column(name = "parents_phone_number")
    private String parentsPhoneNumber;

    @Column(name = "user_id")
    private Integer userId;


}
