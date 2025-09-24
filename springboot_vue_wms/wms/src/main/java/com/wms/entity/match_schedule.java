package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "match_schedule")
@TableName("match_schedule")
public class match_schedule {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "match_round")
    private Integer matchRound; // 轮次

    @Column(name = "group_type", length = 100)
    private String groupType; // 组别

    @Column(name = "player1_id")
    private Integer player1Id; // 选手1 ID

    @Column(name = "player2_id")
    private Integer player2Id; // 选手2 ID

    @Column(name = "court_number")
    private Integer courtNumber; // 球台编号

    @Column(name = "match_time")
    private LocalDateTime matchTime; // 比赛时间

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMatchRound() {
        return matchRound;
    }

    public void setMatchRound(Integer matchRound) {
        this.matchRound = matchRound;
    }

    public Integer getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(Integer player1Id) {
        this.player1Id = player1Id;
    }

    public Integer getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(Integer player2Id) {
        this.player2Id = player2Id;
    }

    public Integer getCourtNumber() {
        return courtNumber;
    }

    public void setCourtNumber(Integer courtNumber) {
        this.courtNumber = courtNumber;
    }

    public LocalDateTime getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(LocalDateTime matchTime) {
        this.matchTime = matchTime;
    }

    @Override
    public String toString() {
        return "match_schedule{" +
                "matchTime=" + matchTime +
                ", courtNumber=" + courtNumber +
                ", player2Id=" + player2Id +
                ", player1Id=" + player1Id +
                ", groupType='" + groupType + '\'' +
                ", matchRound=" + matchRound +
                ", id=" + id +
                '}';
    }
}
