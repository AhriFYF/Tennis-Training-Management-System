package com.wms.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "competition_registrations")
@TableName("competition_registrations")
public class MonthlyCompetitionRegistration {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private student_users student;

    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private MonthlyCompetition competition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompetitionGroup group_team;

    @Column(nullable = false)
    private Boolean isPaid = false;

    @Column(name = "register_time", nullable = false)
    private LocalDateTime registerTime;

    public CompetitionGroup getGroup() {
        return group_team;
    }

    public void setGroup(CompetitionGroup group) {
        this.group_team = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public student_users getStudent() {
        return student;
    }

    public void setStudent(student_users student) {
        this.student = student;
    }

    public MonthlyCompetition getCompetition() {
        return competition;
    }

    public void setCompetition(MonthlyCompetition competition) {
        this.competition = competition;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public LocalDateTime getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDateTime registerTime) {
        this.registerTime = registerTime;
    }

    @Override
    public String toString() {
        return "MonthlyCompetitionRegistration{" +
                "id=" + id +
                ", student=" + student +
                ", competition=" + competition +
                ", group_team=" + group_team +
                ", isPaid=" + isPaid +
                ", registerTime=" + registerTime +
                '}';
    }
}

