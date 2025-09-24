package com.wms.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CoachRegisterDTO {
    private String no;
    private String password;
    private String name;
    private String phone;
    private Integer age;
    private String gender; // M-男, F-女
    private Integer campusId;
    private String email;
    private String coachNo;
    private Integer level;
    private String achievements;

    // 非数据库字段，用于接收前端数据
    private String confirmPassword;

    // 照片文件
    private MultipartFile photo;
}