package com.wms.entity;
import javax.persistence.*;
import java.util.Date;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "campuses")
public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    private String contactPerson;
    private String contactPhone;
    private String contactEmail;

    @ManyToOne
    @JoinColumn(name = "parent_campus_id")
    private Campus parentCampus;

    @Column(name = "is_center", nullable = false)
    private Boolean isCenter = false;

    @Column(name = "create_time", nullable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;


}