package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.User;
import com.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 获取所有用户
    @GetMapping("/list")
    public Result list() {
        return Result.suc(userService.list());
    }

    // 根据账号查询
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getNo, no));
        return user != null ? Result.suc(user) : Result.fail("用户不存在");
    }

    // 新增用户
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        // 验证账号是否已存在
        if(userService.count(new LambdaQueryWrapper<User>().eq(User::getNo, user.getNo())) > 0) {
            return Result.fail("账号已存在");
        }

        // 设置默认值
        if(user.getRoleId() == null) {
            user.setRoleId(3); // 默认为学员
        }
        user.setIsValid("Y"); // 默认有效

        return userService.save(user) ? Result.suc() : Result.fail("保存失败");
    }

    // 更新用户
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        return userService.updateById(user) ? Result.suc() : Result.fail();
    }

    // 删除用户
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        return userService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 登录 - 简化版
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User loginUser = userService.getOne(new LambdaQueryWrapper<User>()
                .eq(User::getNo, user.getNo())
                .eq(User::getPassword, user.getPassword()));

        if(loginUser == null) {
            return Result.fail("账号或密码错误");
        }

        if(!"Y".equals(loginUser.getIsValid())) {
            return Result.fail("账号已被禁用");
        }

        return Result.suc(loginUser);
    }

    // 分页查询
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query) {
        Page<User> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        Map<String, Object> param = query.getParam();
        String name = (String)param.get("name");
        String roleId = (String)param.get("roleId");
        String campusId = (String)param.get("campusId");

        if(StringUtils.isNotBlank(name)) {
            wrapper.like(User::getName, name);
        }
        if(StringUtils.isNotBlank(roleId)) {
            wrapper.eq(User::getRoleId, roleId);
        }
        if(StringUtils.isNotBlank(campusId)) {
            wrapper.eq(User::getCampusId, campusId);
        }

        IPage<User> result = userService.page(page, wrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }

    // 学员注册 - 简化版
    @PostMapping("/registerStudent")
    public Result registerStudent(@RequestBody User user) {
        // 仅验证必填字段
        if(StringUtils.isBlank(user.getNo()) || StringUtils.isBlank(user.getPassword()) ||
                StringUtils.isBlank(user.getName()) || StringUtils.isBlank(user.getPhone())) {
            return Result.fail("请填写完整信息");
        }

        // 设置学员属性
        user.setRoleId(3); // 学员角色
        user.setIsValid("Y");

        return this.save(user);
    }

    // 教练注册 - 简化版
    @PostMapping("/registerCoach")
    public Result registerCoach(@RequestBody User user) {
        // 仅验证必填字段
        if(StringUtils.isBlank(user.getNo()) || StringUtils.isBlank(user.getPassword()) ||
                StringUtils.isBlank(user.getName()) || StringUtils.isBlank(user.getPhone())) {
            return Result.fail("请填写完整信息");
        }

        // 设置教练属性
        user.setRoleId(2); // 教练角色
        user.setIsValid("Y");

        return this.save(user);
    }
}