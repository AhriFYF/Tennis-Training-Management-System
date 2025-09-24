package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "monthly_match_registration")
@TableName("monthly_match_registration")
public class match_registration {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @Column(name = "group_type", length = 100)
    private String groupType; // 组别: '甲', '乙', '丙'

    @Column(name = "registration_fee", precision = 10, scale = 2)
    private BigDecimal registrationFee = new BigDecimal("30.00"); // 报名费: 30.00

    @Column(name = "payment_status", length = 20)
    private String paymentStatus = "未支付"; // '已支付', '未支付'

    @Column(name = "registration_date")
    private LocalDateTime registrationDate; // 报名时间

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public BigDecimal getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(BigDecimal registrationFee) {
        this.registrationFee = registrationFee;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "monthly_match_registration{" +
                "paymentStatus='" + paymentStatus + '\'' +
                ", id=" + id +
                ", studentId=" + studentId +
                ", groupType='" + groupType + '\'' +
                ", registrationFee=" + registrationFee +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
