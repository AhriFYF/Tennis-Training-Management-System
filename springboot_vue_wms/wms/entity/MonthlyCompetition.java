package com.wms.entity;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "monthly_competitions")
public class MonthlyCompetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "campus_id", nullable = false)
    private Campus campus;

    @Column(name = "competition_date", nullable = false)
    private LocalDate competitionDate;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String status = "UPCOMING"; // UPCOMING, ONGOING, FINISHED

    // Getters and Setters
}
