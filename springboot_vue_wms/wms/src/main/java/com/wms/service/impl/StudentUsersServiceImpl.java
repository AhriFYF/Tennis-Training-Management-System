package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.student_users;
import com.wms.mapper.StudentUsersMapper;
import com.wms.service.StudentUsersService;
import org.springframework.stereotype.Service;

@Service
public class StudentUsersServiceImpl extends ServiceImpl<StudentUsersMapper, student_users> implements StudentUsersService {

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
}