package com.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.student_users;

public interface StudentUsersService extends IService<student_users> {
    /**
     * 根据用户ID查找学生信息
     */
    student_users findByUserId(Integer userId);

    /**
     * 根据学号查找学生信息
     */
    student_users findByStudentNo(String studentNo);
}