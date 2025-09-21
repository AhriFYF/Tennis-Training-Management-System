package com.wms.entity;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_logs")
@TableName("system_logs")
public class SystemLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String actionType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String actionDetail;

    @Column(name = "action_time", nullable = false)
    private LocalDateTime actionTime;

    private String ipAddress;

    @Column(columnDefinition = "TEXT")
    private String requestData;

    public LocalDateTime getActionTime() {
        return actionTime;
    }

    public void setActionTime(LocalDateTime actionTime) {
        this.actionTime = actionTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionDetail() {
        return actionDetail;
    }

    public void setActionDetail(String actionDetail) {
        this.actionDetail = actionDetail;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    @Override
    public String toString() {
        return "SystemLog{" +
                "id=" + id +
                ", user=" + user +
                ", actionType='" + actionType + '\'' +
                ", actionDetail='" + actionDetail + '\'' +
                ", actionTime=" + actionTime +
                ", ipAddress='" + ipAddress + '\'' +
                ", requestData='" + requestData + '\'' +
                '}';
    }
}
