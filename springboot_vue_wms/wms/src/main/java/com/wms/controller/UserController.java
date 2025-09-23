package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.common.Loggable;
import com.wms.entity.Menu;
import com.wms.entity.User;
import com.wms.service.MenuService;
import com.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    // 获取所有用户列表（未分页）
    @GetMapping("/list")
    public List<User> list() {
        return userService.list();
    }

    // 根据账号查询用户
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        List<User> list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    // 新增用户（通用保存）
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        return userService.save(user) ? Result.suc() : Result.fail();
    }

    // 更新用户
    @PostMapping("/update")
    @Loggable(actionType = "更新", actionDetail = "用户更新了个人信息")
    public Result update(@RequestBody User user) {
        return userService.updateById(user) ? Result.suc() : Result.fail();
    }

    // 删除用户
    @GetMapping("/del")
    @Loggable(actionType = "删除", actionDetail = "管理员删除了用户")
    public Result del(@RequestParam String id) {
        return userService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        List<User> list = userService.lambdaQuery()
                .eq(User::getNo, user.getNo())
                .eq(User::getPassword, user.getPassword())
                .list();

        if (list.size() > 0) {
            User loginUser = list.get(0);
            // 查询该角色对应的菜单权限
            List<Menu> menuList = menuService.lambdaQuery()
                    .like(Menu::getMenuright, loginUser.getRoleId())
                    .list();

            HashMap<String, Object> res = new HashMap<>();
            res.put("user", loginUser);
            res.put("menu", menuList);
            return Result.suc(res);
        }
        return Result.fail("用户名或密码错误");
    }

    // 修改用户（别名）
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }

    // 新增或修改（通用保存或更新）
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    // 删除用户（按ID）
    @GetMapping("/delete")
    public boolean delete(Integer id) {
        return userService.removeById(id);
    }

    // 模糊查询用户（按姓名）
    @PostMapping("/listP")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(user.getName())) {
            wrapper.like(User::getName, user.getName());
        }
        return Result.suc(userService.list(wrapper));
    }

    // 分页查询（按姓名模糊查）
    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query) {
        String name = (String) query.getParam().get("name");
        Page<User> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(User::getName, name);
        }
        IPage<User> result = userService.page(page, wrapper);
        return result.getRecords();
    }

    // 分页查询（校区等条件，扩展用）
    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        Page<User> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            wrapper.like(User::getName, name);
        }
        IPage<User> result = userService.pageCC(page, wrapper);
        return result.getRecords();
    }

    // 分页多条件查询（带角色、性别、校区等）
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String sex = (String) param.get("sex");
        String roleId = (String) param.get("roleId");
        String campusId = String.valueOf(param.get("campusId"));

        Page<User> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            wrapper.like(User::getName, name);
        }
        if (StringUtils.isNotBlank(sex)) {
            wrapper.eq(User::getSex, sex);
        }
        if (StringUtils.isNotBlank(roleId)) {
            wrapper.eq(User::getRoleId, roleId);
        }
        if (!"0".equals(campusId) && StringUtils.isNotBlank(campusId)) {
            wrapper.eq(User::getCampusId, campusId);
        }

        IPage<User> result = userService.pageCC(page, wrapper);
        return Result.suc(result.getRecords(), result.getTotal());
    }

    // ========== 【学员注册接口】 ==========
    // 接收前端提交的 User 对象，设置 roleId=3（学员），保存到数据库
    @PostMapping("/registerStudent")
    public Result registerStudent(@RequestBody User user) {
        // 基础非空校验
        if (StringUtils.isBlank(user.getNo()) ||
                StringUtils.isBlank(user.getPassword()) ||
                StringUtils.isBlank(user.getName()) ||
                StringUtils.isBlank(user.getPhone())) {
            return Result.fail("请填写用户名、密码、姓名、电话");
        }

        // 设置为学员角色
        user.setRoleId(3); // 3 = 学员
        user.setIsvalid("Y"); // 默认有效

        return this.save(user); // 调用通用保存方法
    }

    // ========== 【教练注册接口】 ==========
    @PostMapping("/registerCoach")
    public Result registerCoach(@RequestBody User user) {
        // 基础非空校验
        if (StringUtils.isBlank(user.getNo()) ||
                StringUtils.isBlank(user.getPassword()) ||
                StringUtils.isBlank(user.getName()) ||
                StringUtils.isBlank(user.getPhone())) {
            return Result.fail("请填写用户名、密码、姓名、电话");
        }

        // 设置为教练角色
        user.setRoleId(2); // 2 = 教练
        user.setIsvalid("Y"); // 默认有效

        return this.save(user); // 调用通用保存方法
    }
}