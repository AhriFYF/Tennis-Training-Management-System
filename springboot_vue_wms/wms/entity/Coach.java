package com.wms.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "coaches")
@PrimaryKeyJoinColumn(name = "user_id")
public class Coach extends User {
    private String photoUrl;

    @Column(columnDefinition = "TEXT")
    private String achievements;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CoachLevel level;

    @Column(name = "hourly_rate", nullable = false)
    private BigDecimal hourlyRate;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;

    @OneToMany(mappedBy = "coach")
    private Set<CoachStudentRelation> studentRelations = new HashSet<>();

    @OneToMany(mappedBy = "coach")
    private Set<CourseSchedule> courseSchedules = new HashSet<>();

    // Getters and Setters
}

