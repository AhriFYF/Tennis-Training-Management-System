package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.dto.StudentDetailDTO;
import com.wms.entity.student_users;
import com.wms.mapper.StudentUsersMapper;
import com.wms.service.CampusService;
import com.wms.service.StudentUsersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StudentUsersServiceImpl extends ServiceImpl<StudentUsersMapper, student_users> implements StudentUsersService {

    @Autowired
    private CampusService campusService;

    @Override
    public student_users findByUserId(Integer userId) {
        LambdaQueryWrapper<student_users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(student_users::getUserId, userId);
        return this.getOne(wrapper);
    }

    @Override
    public student_users findByStudentNo(String studentNo) {
        LambdaQueryWrapper<student_users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(student_users::getStudentNo, studentNo);
        return this.getOne(wrapper);
    }

    @Override
    public StudentDetailDTO findStudentDetailByStudentNo(String studentNo) {
        try {
            // 1. 查询基础学生信息
            student_users student = this.findByStudentNo(studentNo);
            if (student == null) {
                log.warn("未找到学号为 {} 的学生信息", studentNo);
                return null;
            }

            // 2. 组装详细信息
            return assembleStudentDetail(student);
        } catch (Exception e) {
            log.error("查询学号 {} 的学生详情失败: {}", studentNo, e.getMessage());
            throw new RuntimeException("查询学生详情失败", e);
        }
    }
    @Override
    public StudentDetailDTO findStudentDetailByUserId(Integer userId) {
        try {
            // 1. 根据userId查询基础学生信息
            student_users student = this.findByUserId(userId);
            if (student == null) {
                log.warn("未找到用户ID为 {} 的学生信息", userId);
                return null;
            }

            // 2. 组装详细信息（复用已有的私有方法）
            return assembleStudentDetail(student);
        } catch (Exception e) {
            log.error("查询用户ID {} 的学生详情失败: {}", userId, e.getMessage());
            throw new RuntimeException("查询学生详情失败", e);
        }
    }

    /**
     * 组装学生详细信息（包含校区名称）
     */
    private StudentDetailDTO assembleStudentDetail(student_users student) {
        StudentDetailDTO detailDTO = new StudentDetailDTO();

        // 复制基础属性
        BeanUtils.copyProperties(student, detailDTO);

        // 获取并设置校区名称
        if (student.getCampusId() != null) {
            try {
                String campusName = campusService.getCampusNameById(student.getCampusId());
                detailDTO.setCampusName(campusName);
            } catch (Exception e) {
                log.warn("获取校区ID {} 的名称失败: {}", student.getCampusId(), e.getMessage());
                detailDTO.setCampusName("未知校区");
            }
        } else {
            detailDTO.setCampusName("未分配校区");
        }

        return detailDTO;
    }
}