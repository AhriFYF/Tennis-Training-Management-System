package com.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.dto.StudentUpdateDTO;
import com.wms.entity.student_users;
import com.wms.dto.StudentRegisterDTO;
import com.wms.dto.StudentDetailDTO;

public interface StudentService extends IService<student_users> {
    /**
     * 学生注册（同时插入user表和student_users表）
     */
    boolean registerStudent(StudentRegisterDTO dto);

    /**
     * 更新学生个人信息
     */
    boolean updateStudentProfile(Integer studentId, StudentUpdateDTO updatedInfo);

    /**
     * 根据学号查找学生（联合查询）
     */
    StudentDetailDTO getStudentByNo(String no);

    /**
     * 根据学生ID获取学生详细信息
     */
    StudentDetailDTO getStudentDetail(Integer studentId);

    /**
     * 根据用户ID获取学生详细信息
     */
    StudentDetailDTO getStudentDetailByUserId(Integer userId);

    StudentDetailDTO findStudentDetailByStudentNo(String studentNo);

    StudentDetailDTO findStudentDetailByUserId(Integer userId);

    student_users findByUserId(Integer userId);

    student_users findByStudentNo(String studentNo);
}