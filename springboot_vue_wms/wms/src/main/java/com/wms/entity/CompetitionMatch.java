package com.wms.entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "competition_matches")
public class CompetitionMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "competition_id", nullable = false)
    private MonthlyCompetition competition;

    @ManyToOne
    @JoinColumn(name = "player1_id", nullable = false)
    private student_users player1;

    @ManyToOne
    @JoinColumn(name = "player2_id", nullable = false)
    private student_users player2;

    @Column(nullable = false)
    private Integer round;

    @Column(nullable = false)
    private Integer tableNumber;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    private Integer player1Score;
    private Integer player2Score;

    @Column(nullable = false)
    private String status = "SCHEDULED"; // SCHEDULED, ONGOING, FINISHED

    // Getters and Setters
}
