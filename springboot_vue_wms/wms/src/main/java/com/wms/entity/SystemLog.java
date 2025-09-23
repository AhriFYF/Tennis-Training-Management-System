package com.wms.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "system_logs")
@TableName("system_logs")
public class SystemLog {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @TableField("user_id")
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String actionType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String actionDetail;

    @Column(name = "action_time", nullable = false)
    private LocalDateTime actionTime;

    private String ipAddress;

    @Column(columnDefinition = "TEXT")
    private String requestData;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActionDetail() {
        return actionDetail;
    }

    public void setActionDetail(String actionDetail) {
        this.actionDetail = actionDetail;
    }

    public LocalDateTime getActionTime() {
        return actionTime;
    }

    public void setActionTime(LocalDateTime actionTime) {
        this.actionTime = actionTime;
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
                "actionDetail='" + actionDetail + '\'' +
                ", id=" + id +
                ", user=" + userId +
                ", actionType='" + actionType + '\'' +
                ", actionTime=" + actionTime +
                ", ipAddress='" + ipAddress + '\'' +
                ", requestData='" + requestData + '\'' +
                '}';
    }
}
