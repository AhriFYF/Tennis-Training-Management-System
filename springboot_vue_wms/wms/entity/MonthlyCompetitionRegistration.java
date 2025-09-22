package com.wms.entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "competition_registrations")
public class MonthlyCompetitionRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private MonthlyCompetition competition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompetitionGroup group;

    @Column(nullable = false)
    private Boolean isPaid = false;

    @Column(name = "register_time", nullable = false)
    private LocalDateTime registerTime;

    // Getters and Setters
}

