package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="")
@Table(name="user")
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "账号")
    private String no;

    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    private Integer age;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "角色 0主校区管理员，1分校区管理员，2教练，3学生")
    private Integer roleId;

    @ApiModelProperty(value = "是否有效，Y有效，其他无效")
    @TableField("isvalid")
    private String isvalid;

    @TableField("campus_id")
    @ApiModelProperty(value = "所属校区ID")
    private Integer campusId;

    @ApiModelProperty(value = "账户余额")
    @TableField("balance")
    private BigDecimal balance = BigDecimal.ZERO; // 默认余额为0

    // 超级管理员密钥相关字段
    @ApiModelProperty(value = "超级管理员密钥")
    @TableField("super_admin_key")
    private String superAdminKey;

    @ApiModelProperty(value = "密钥创建时间")
    @TableField("key_created_time")
    private LocalDateTime keyCreatedTime;

    @ApiModelProperty(value = "密钥过期时间")
    @TableField("key_expired_time")
    private LocalDateTime keyExpiredTime;

    @ApiModelProperty(value = "设备标识")
    @TableField("device_id")
    private String deviceId;


    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(String isvalid) {
        this.isvalid = isvalid;
    }

    public Integer getCampusId() {
        return campusId;
    }

    public void setCampusId(Integer campusId) {
        this.campusId = campusId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getSuperAdminKey() {
        return superAdminKey;
    }

    public void setSuperAdminKey(String superAdminKey) {
        this.superAdminKey = superAdminKey;
    }

    public LocalDateTime getKeyCreatedTime() {
        return keyCreatedTime;
    }

    public void setKeyCreatedTime(LocalDateTime keyCreatedTime) {
        this.keyCreatedTime = keyCreatedTime;
    }

    public LocalDateTime getKeyExpiredTime() {
        return keyExpiredTime;
    }

    public void setKeyExpiredTime(LocalDateTime keyExpiredTime) {
        this.keyExpiredTime = keyExpiredTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", roleId=" + roleId +
                ", isvalid='" + isvalid + '\'' +
                ", campusId=" + campusId +
                ", balance=" + balance +
                ", superAdminKey='" + superAdminKey + '\'' +
                ", keyCreatedTime=" + keyCreatedTime +
                ", keyExpiredTime=" + keyExpiredTime +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}