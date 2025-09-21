package com.wms.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "campuses")
public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(name = "contact_person", length = 50)
    private String contactPerson;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(name = "admin_id")
    private Long adminId; // 校区管理员ID（关联用户表）

    @Column(name = "parent_campus_id")
    private Long parentCampusId; // 父校区ID，null表示中心校区

    @Column(name = "is_center", nullable = false)
    private Boolean isCenter = false; // 是否为中心校区

    @Column(name = "coach_fee_advanced")
    private Integer coachFeeAdvanced = 200; // 高级教练收费（元/小时）

    @Column(name = "coach_fee_intermediate")
    private Integer coachFeeIntermediate = 150; // 中级教练收费（元/小时）

    @Column(name = "coach_fee_primary")
    private Integer coachFeePrimary = 80; // 初级教练收费（元/小时）

    @Column(name = "status", nullable = false)
    private Integer status = 1; // 状态：0-禁用，1-启用

    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "creator_id", nullable = false)
    private Long creatorId; // 创建者ID（超级管理员）

    // 省略getter和setter方法...

    // 新增方法：判断是否为分校区
    public boolean isBranchCampus() {
        return !isCenter && parentCampusId != null;
    }

    // 获取对应级别的教练费用
    public Integer getCoachFeeByLevel(Integer coachLevel) {
        switch (coachLevel) {
            case 1: return coachFeePrimary;
            case 2: return coachFeeIntermediate;
            case 3: return coachFeeAdvanced;
            default: return coachFeePrimary;
        }
    }

    // 以下是原有的getter和setter方法，保持不变
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getParentCampusId() {
        return parentCampusId;
    }

    public void setParentCampusId(Long parentCampusId) {
        this.parentCampusId = parentCampusId;
    }

    public Boolean getIsCenter() {
        return isCenter;
    }

    public void setIsCenter(Boolean center) {
        isCenter = center;
    }

    public Integer getCoachFeeAdvanced() {
        return coachFeeAdvanced;
    }

    public void setCoachFeeAdvanced(Integer coachFeeAdvanced) {
        this.coachFeeAdvanced = coachFeeAdvanced;
    }

    public Integer getCoachFeeIntermediate() {
        return coachFeeIntermediate;
    }

    public void setCoachFeeIntermediate(Integer coachFeeIntermediate) {
        this.coachFeeIntermediate = coachFeeIntermediate;
    }

    public Integer getCoachFeePrimary() {
        return coachFeePrimary;
    }

    public void setCoachFeePrimary(Integer coachFeePrimary) {
        this.coachFeePrimary = coachFeePrimary;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String toString() {
        return "Campus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", adminId=" + adminId +
                ", parentCampusId=" + parentCampusId +
                ", isCenter=" + isCenter +
                ", coachFeeAdvanced=" + coachFeeAdvanced +
                ", coachFeeIntermediate=" + coachFeeIntermediate +
                ", coachFeePrimary=" + coachFeePrimary +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", creatorId=" + creatorId +
                '}';
    }
}