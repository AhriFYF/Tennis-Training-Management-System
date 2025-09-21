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

    public LocalDate getCompetitionDate() {
        return competitionDate;
    }

    public void setCompetitionDate(LocalDate competitionDate) {
        this.competitionDate = competitionDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MonthlyCompetition{" +
                "id=" + id +
                ", campus=" + campus +
                ", competitionDate=" + competitionDate +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
