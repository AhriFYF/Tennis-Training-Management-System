package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wms.common.Result;
import com.wms.dto.StudentRegisterDTO;
import com.wms.dto.StudentDetailDTO;
import com.wms.entity.User;
import com.wms.entity.student_users;
import com.wms.service.StudentService;
import com.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    /**
     * 学生注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody StudentRegisterDTO dto) {
        try {
            boolean success = studentService.registerStudent(dto);
            return success ? Result.suc("注册成功") : Result.fail("注册失败");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 获取当前登录学生详细信息
     */
    @GetMapping("/profile")
    public Result getProfile(HttpServletRequest request) {
        // 从session获取用户ID
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            return Result.fail("未登录或会话已过期");
        }

        StudentDetailDTO studentDetail = studentService.getStudentDetailByUserId(userId);
        if (studentDetail == null) {
            return Result.fail("当前用户不是学生或不存在");
        }

        return Result.suc(studentDetail);
    }

    /**
     * 更新学生个人信息
     */
    @PutMapping("/profile")
    public Result updateProfile(@RequestBody StudentRegisterDTO updatedInfo, HttpServletRequest request) {
        // 从session获取用户ID
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            return Result.fail("未登录或会话已过期");
        }

        // 先获取学生ID
        StudentDetailDTO studentDetail = studentService.getStudentDetailByUserId(userId);
        if (studentDetail == null) {
            return Result.fail("当前用户不是学生或不存在");
        }

        try {
            boolean success = studentService.updateStudentProfile(studentDetail.getStudentId(), updatedInfo);
            return success ? Result.suc("更新成功") : Result.fail("更新失败");
        } catch (Exception e) {
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 根据学号查询学生详细信息
     */
    @GetMapping("/byNo/{no}")
    public Result getStudentByNo(@PathVariable String no) {
        StudentDetailDTO student = studentService.getStudentByNo(no);
        if (student == null) {
            return Result.fail("学生不存在");
        }
        return Result.suc(student);
    }

    /**
     * 获取所有学生列表
     */
    @GetMapping("/list")
    public Result getAllStudents() {
        LambdaQueryWrapper<student_users> wrapper = new LambdaQueryWrapper<>();
        List<student_users> students = studentService.list(wrapper);
        return Result.suc(students);
    }

    /**
     * 根据学生ID获取详细信息
     */
    @GetMapping("/{studentId}")
    public Result getStudentById(@PathVariable Integer studentId) {
        StudentDetailDTO studentDetail = studentService.getStudentDetail(studentId);
        if (studentDetail == null) {
            return Result.fail("学生不存在");
        }
        return Result.suc(studentDetail);
    }
}