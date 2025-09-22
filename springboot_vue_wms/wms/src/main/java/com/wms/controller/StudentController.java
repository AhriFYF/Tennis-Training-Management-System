package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Result;
import com.wms.entity.User;
import com.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserService userService;

    // ======================
    // 1. 获取学员个人信息
    // ======================
    @GetMapping("/info")
    public Result getStudentInfo() {
        // TODO: 从登录态获取当前用户 no，这里模拟
        String currentNo = "student001";

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNo, currentNo);
        List<User> users = userService.list(wrapper);

        if (users.isEmpty()) {
            return Result.fail("未找到学员信息");
        }

        User student = users.get(0);
        if (student.getRoleId() != 3) {
            return Result.fail("当前用户不是学员");
        }

        return Result.suc(student);
    }

    // ======================
    // 2. 修改学员个人信息
    // ======================
    @PutMapping("/updateProfile")
    public Result updateProfile(@RequestBody User updatedUser) {
        String no = updatedUser.getNo();
        if (no == null) {
            return Result.fail("用户账号不能为空");
        }

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNo, no);
        List<User> users = userService.list(wrapper);

        if (users.isEmpty()) {
            return Result.fail("未找到该学员");
        }

        User existingUser = users.get(0);
        if (existingUser.getRoleId() != 3) {
            return Result.fail("当前用户不是学员");
        }

        // 可选：只允许更新部分字段，避免误操作
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setSex(updatedUser.getSex());
        //existingUser.setEmail(updatedUser.getEmail());

        boolean success = userService.updateById(existingUser);
        return success ? Result.suc() : Result.fail("更新失败");
    }

    // ======================
    // 3. 查询教练列表（按条件）
    // ======================
    @GetMapping("/coach/list")
    public Result getCoachList(
            @RequestParam(required = false) Integer campusId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer sex,
            @RequestParam(required = false) Integer age
    ) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getRoleId, 2); // 教练

        if (campusId != null) {
            wrapper.eq(User::getCampusId, campusId);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(User::getName, name);
        }
        if (sex != null) {
            wrapper.eq(User::getSex, sex);
        }
        if (age != null) {
            wrapper.eq(User::getAge, age);
        }

        List<User> coaches = userService.list(wrapper);
        return Result.suc(coaches);
    }

    // ======================
    // 4. 申请教练（仅声明，不实现功能）
    // ======================
    // ⚠️ 您要求：不实现申请教练功能（无接口、不保存数据）
    // 所以此处不提供 POST /student/coach/apply 接口
}