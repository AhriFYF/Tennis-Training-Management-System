package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wms.dto.StudentRegisterDTO;
import com.wms.dto.StudentDetailDTO;
import com.wms.entity.User;
import com.wms.entity.student_users;
import com.wms.mapper.UserMapper;
import com.wms.mapper.StudentUsersMapper;
import com.wms.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentUsersMapper, student_users> implements StudentService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentUsersMapper studentUsersMapper;

    @Override
    @Transactional
    public boolean registerStudent(StudentRegisterDTO dto) {
        // 手动验证参数
        if (StringUtils.isEmpty(dto.getNo())) {
            throw new RuntimeException("学号不能为空");
        }
        if (StringUtils.isEmpty(dto.getPassword())) {
            throw new RuntimeException("密码不能为空");
        }
        if (dto.getPassword().length() < 6) {
            throw new RuntimeException("密码长度至少6位");
        }
        if (StringUtils.isEmpty(dto.getName())) {
            throw new RuntimeException("姓名不能为空");
        }
        if (StringUtils.isEmpty(dto.getPhone())) {
            throw new RuntimeException("手机号不能为空");
        }
        if (dto.getGender() != null && !"M".equals(dto.getGender()) && !"F".equals(dto.getGender())) {
            throw new RuntimeException("性别格式不正确，M-男, F-女");
        }

        // 1. 检查学号是否已存在
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getNo, dto.getNo());
        if (userMapper.selectCount(userWrapper) > 0) {
            throw new RuntimeException("学号已存在");
        }

        // 2. 插入user表（仅存储基础信息）
        User user = new User();
        user.setNo(dto.getNo());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setAge(dto.getAge());
        user.setRoleId(3); // 学生角色
        user.setIsvalid("Y"); // 默认有效
        user.setCampusId(dto.getCampusId());

        // 处理性别字段（user表使用sex字段，0-女，1-男）
        if (dto.getGender() != null) {
            if ("M".equals(dto.getGender())) {
                user.setSex(1);
            } else if ("F".equals(dto.getGender())) {
                user.setSex(0);
            }
        }

        int userInserted = userMapper.insert(user);
        if (userInserted <= 0) {
            throw new RuntimeException("用户信息保存失败");
        }

        // 3. 插入student_users表（存储学生特有信息）
        student_users studentUser = new student_users();
        BeanUtils.copyProperties(dto, studentUser);
        studentUser.setUserId(user.getId()); // 设置关联的用户ID

        int studentInserted = studentUsersMapper.insert(studentUser);
        if (studentInserted <= 0) {
            throw new RuntimeException("学生信息保存失败");
        }

        return true;
    }

    @Override
    @Transactional
    public boolean updateStudentProfile(Integer studentId, StudentRegisterDTO updatedInfo) {
        // 1. 获取学生信息
        student_users student = studentUsersMapper.selectById(studentId);
        if (student == null) {
            throw new RuntimeException("学生信息不存在");
        }

        // 2. 更新student_users表
        if (updatedInfo.getClassGrade() != null) {
            student.setClassGrade(updatedInfo.getClassGrade());
        }
//        if (updatedInfo.getTrainingHours() != null) {
//            student.setTrainingHours(updatedInfo.getTrainingHours());
//        }
//        if (updatedInfo.getPaymentStatus() != null) {
//            student.setPaymentStatus(updatedInfo.getPaymentStatus());
//        }

        int studentUpdated = studentUsersMapper.updateById(student);
        if (studentUpdated <= 0) {
            throw new RuntimeException("学生信息更新失败");
        }

        // 3. 更新user表（仅更新基础信息）
        User user = userMapper.selectById(student.getUserId());
        if (user == null) {
            throw new RuntimeException("关联的用户信息不存在");
        }

        if (updatedInfo.getName() != null) {
            user.setName(updatedInfo.getName());
        }
        if (updatedInfo.getPhone() != null) {
            user.setPhone(updatedInfo.getPhone());
        }
        if (updatedInfo.getAge() != null) {
            user.setAge(updatedInfo.getAge());
        }
        // 处理性别字段
        if (updatedInfo.getGender() != null) {
            if ("M".equals(updatedInfo.getGender())) {
                user.setSex(1);
            } else if ("F".equals(updatedInfo.getGender())) {
                user.setSex(0);
            }
        }

        int userUpdated = userMapper.updateById(user);
        if (userUpdated <= 0) {
            throw new RuntimeException("用户信息更新失败");
        }

        return true;
    }

    @Override
    public StudentDetailDTO getStudentByNo(String no) {
        // 先通过学号查询用户
        LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.eq(User::getNo, no).eq(User::getRoleId, 3);
        User user = userMapper.selectOne(userWrapper);

        if (user == null) {
            return null;
        }

        // 再通过用户ID查询学生信息
        return getStudentDetailByUserId(user.getId());
    }

    @Override
    public StudentDetailDTO getStudentDetail(Integer studentId) {
        student_users student = studentUsersMapper.selectById(studentId);
        if (student == null) {
            return null;
        }

        return getStudentDetailByUserId(student.getUserId());
    }

    @Override
    public StudentDetailDTO getStudentDetailByUserId(Integer userId) {
        // 获取用户信息
        User user = userMapper.selectById(userId);
        if (user == null || !user.getRoleId().equals(3)) {
            return null;
        }

        // 获取学生信息
        LambdaQueryWrapper<student_users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(student_users::getUserId, userId);
        student_users student = studentUsersMapper.selectOne(wrapper);

        if (student == null) {
            return null;
        }

        // 组合数据
        StudentDetailDTO detail = new StudentDetailDTO();
        BeanUtils.copyProperties(student, detail);
        detail.setNo(user.getNo());
        detail.setCampusId(user.getCampusId());

        return detail;
    }
}