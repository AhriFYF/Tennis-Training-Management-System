package com.wms.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "competition_matches")
@TableName("competition_matches")
public class CompetitionMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableId(value = "id", type = IdType.AUTO)
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

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonthlyCompetition getCompetition() {
        return competition;
    }

    public void setCompetition(MonthlyCompetition competition) {
        this.competition = competition;
    }

    public student_users getPlayer1() {
        return player1;
    }

    public void setPlayer1(student_users player1) {
        this.player1 = player1;
    }

    public student_users getPlayer2() {
        return player2;
    }

    public void setPlayer2(student_users player2) {
        this.player2 = player2;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getPlayer1Score() {
        return player1Score;
    }

    public void setPlayer1Score(Integer player1Score) {
        this.player1Score = player1Score;
    }

    public Integer getPlayer2Score() {
        return player2Score;
    }

    public void setPlayer2Score(Integer player2Score) {
        this.player2Score = player2Score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CompetitionMatch{" +
                "player1Score=" + player1Score +
                ", id=" + id +
                ", competition=" + competition +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", round=" + round +
                ", tableNumber=" + tableNumber +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", player2Score=" + player2Score +
                ", status='" + status + '\'' +
                '}';
    }
}
