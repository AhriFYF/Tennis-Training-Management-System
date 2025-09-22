package com.wms.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {
    @Column(name = "account_balance", nullable = false)
    private BigDecimal accountBalance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "student")
    private Set<CoachStudentRelation> coachRelations = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<CourseBooking> courseBookings = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<MonthlyCompetitionRegistration> competitionRegistrations = new HashSet<>();

    // Getters and Setters
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Set<CoachStudentRelation> getCoachRelations() {
        return coachRelations;
    }

    public void setCoachRelations(Set<CoachStudentRelation> coachRelations) {
        this.coachRelations = coachRelations;
    }

    public Set<CourseBooking> getCourseBookings() {
        return courseBookings;
    }

    public void setCourseBookings(Set<CourseBooking> courseBookings) {
        this.courseBookings = courseBookings;
    }

    public Set<MonthlyCompetitionRegistration> getCompetitionRegistrations() {
        return competitionRegistrations;
    }

    public void setCompetitionRegistrations(Set<MonthlyCompetitionRegistration> competitionRegistrations) {
        this.competitionRegistrations = competitionRegistrations;
    }
}