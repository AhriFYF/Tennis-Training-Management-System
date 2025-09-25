package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.common.Loggable;
import com.wms.dto.CoachRegisterDTO;
import com.wms.dto.StudentRegisterDTO;
import com.wms.entity.Menu;
import com.wms.entity.User;
import com.wms.entity.coach_users;
import com.wms.entity.student_users;
import com.wms.service.MenuService;
import com.wms.service.UserService;
import com.wms.service.StudentUsersService;
import com.wms.service.CoachUsersService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private StudentUsersService studentUsersService;

    @Autowired
    private CoachUsersService coachUsersService;

    // 获取所有用户列表（未分页）
    @GetMapping("/list")
    @Loggable(actionType = "获取 | 用户", actionDetail = "访问数据库获取信息")
    public List<User> list() {
        return userService.list();
    }

    // 根据账号查询用户
    @GetMapping("/findByNo")
    @Loggable(actionType = "查询 | 用户", actionDetail = "访问数据库查询信息")
    public Result findByNo(@RequestParam String no) {
        List<User> list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    // 新增用户（通用保存）
    @PostMapping("/save")
    @Loggable(actionType = "新增 | 用户", actionDetail = "新增信息")
    public Result save(@RequestBody User user) {
        return userService.save(user) ? Result.suc() : Result.fail();
    }

    // 更新用户
    @PostMapping("/update")
    @Loggable(actionType = "更新 | 用户", actionDetail = "更新信息")
    public Result update(@RequestBody User user) {
        return userService.updateById(user) ? Result.suc() : Result.fail();
    }

    // 删除用户
    @GetMapping("/del")
    @Loggable(actionType = "删除 | 用户", actionDetail = "删除信息")
    public Result del(@RequestParam String id) {
        return userService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletRequest request) {
        // 1. 查询数据库中是否存在匹配的用户
        List<User> userList = userService.lambdaQuery()
                .eq(User::getNo, user.getNo())
                .eq(User::getPassword, user.getPassword())
                .list();

        // 2. 检查用户是否存在
        if (userList.size() > 0) {
            // 登录成功，获取用户信息
            User loginUser = userList.get(0);

            // 打印会话 ID
            System.out.println("Login Request Session ID: " + request.getSession().getId());

            // 这行代码将用户的 ID 存储到 HttpSession 中，以便后续的请求可以访问到
            System.out.println("Login success, user ID is: " + loginUser.getId());
            System.out.println("Login success, campus ID is: " + loginUser.getCampusId());
            request.getSession().setAttribute("userId", loginUser.getId());

            // 3. 如果是超级管理员，检查是否需要密钥验证
            if (loginUser.getRoleId() == 0) { // 0表示超级管理员
                // 检查是否已生成密钥
                if (loginUser.getSuperAdminKey() == null || loginUser.getSuperAdminKey().isEmpty()) {
                    // 未生成密钥，需要生成
                    HashMap<String, Object> res = new HashMap<>();
                    res.put("user", loginUser);
                    res.put("needKeyGeneration", true);
                    return Result.suc(res, 200L);
                } else {
                    // 已有密钥，检查是否已验证
                    Boolean keyVerified = (Boolean) request.getSession().getAttribute("superAdminKeyVerified");
                    if (keyVerified == null || !keyVerified) {
                        // 未验证，需要验证密钥
                        HashMap<String, Object> res = new HashMap<>();
                        res.put("user", loginUser);
                        res.put("needKeyVerification", true);
                        return Result.suc(res, 200L);
                    }
                }
            }

            // 4. 查询该角色对应的菜单权限
            List<Menu> menuList = menuService.lambdaQuery()
                    .like(Menu::getMenuright, loginUser.getRoleId())
                    .list();

            // 5. 构建返回给前端的数据
            HashMap<String, Object> res = new HashMap<>();
            res.put("user", loginUser);
            res.put("menu", menuList);

            // 返回成功结果
            return Result.suc(res);
        }

        // 用户名或密码错误，返回失败结果
        return Result.fail("用户名或密码错误");
    }

    // 为超级管理员生成密钥
    @PostMapping("/generateSuperAdminKey")
    @Loggable(actionType = "生成 | 超级管理员密钥", actionDetail = "为超级管理员生成唯一密钥")
    public Result generateSuperAdminKey(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Integer userId = (Integer) params.get("userId");
        String deviceId = (String) params.get("deviceId");
        
        // 参数校验
        if (userId == null || deviceId == null || deviceId.isEmpty()) {
            return Result.fail("参数错误");
        }
        
        // 查询用户
        User user = userService.getById(userId);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        
        // 检查是否为超级管理员
        if (user.getRoleId() != 0) {
            return Result.fail("只有超级管理员可以生成密钥");
        }
        
        // 生成唯一密钥 (使用UUID)
        String key = UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 16);
        
        // 设置密钥信息
        user.setSuperAdminKey(key);
        user.setDeviceId(deviceId);
        LocalDateTime now = LocalDateTime.now();
        user.setKeyCreatedTime(now);
        user.setKeyExpiredTime(now.plusYears(1)); // 有效期一年
        
        // 保存更新
        if (userService.updateById(user)) {
            // 将密钥验证状态存入session
            request.getSession().setAttribute("superAdminKeyVerified", true);
            
            Map<String, Object> result = new HashMap<>();
            result.put("key", key);
            result.put("expiredTime", user.getKeyExpiredTime());
            return Result.suc(result, 200L);
        } else {
            return Result.fail("密钥生成失败");
        }
    }
    
    // 验证超级管理员密钥
    @PostMapping("/verifySuperAdminKey")
    @Loggable(actionType = "验证 | 超级管理员密钥", actionDetail = "验证超级管理员密钥")
    public Result verifySuperAdminKey(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Integer userId = (Integer) params.get("userId");
        String key = (String) params.get("key");
        String deviceId = (String) params.get("deviceId");
        
        // 参数校验
        if (userId == null || key == null || key.isEmpty() || deviceId == null || deviceId.isEmpty()) {
            return Result.fail("参数错误");
        }
        
        // 查询用户
        User user = userService.getById(userId);
        if (user == null) {
            return Result.fail("用户不存在");
        }
        
        // 检查是否为超级管理员
        if (user.getRoleId() != 0) {
            return Result.fail("只有超级管理员需要验证密钥");
        }
        
        // 检查密钥是否存在
        if (user.getSuperAdminKey() == null || user.getSuperAdminKey().isEmpty()) {
            return Result.fail("未生成密钥，请先生成密钥");
        }
        
        // 检查密钥是否正确
        if (!user.getSuperAdminKey().equals(key)) {
            return Result.fail("密钥错误");
        }
        
        // 检查密钥是否过期
        LocalDateTime now = LocalDateTime.now();
        if (user.getKeyExpiredTime().isBefore(now)) {
            return Result.fail("密钥已过期，请重新生成");
        }
        
        // 检查设备是否匹配
        if (!user.getDeviceId().equals(deviceId)) {
            return Result.fail("设备不匹配，密钥只能用于一台设备");
        }
        
        // 验证通过，将验证状态存入session
        request.getSession().setAttribute("superAdminKeyVerified", true);
        
        return Result.suc("密钥验证成功");
    }

    // 修改用户（别名）
    @PostMapping("/mod")
    @Loggable(actionType = "修改 | 用户", actionDetail = "修改信息")
    public boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }

    // 新增或修改（通用保存或更新）
    @PostMapping("/saveOrMod")
    @Loggable(actionType = "修改 | 用户", actionDetail = "修改信息")
    public boolean saveOrMod(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    // 删除用户（按ID）
    @GetMapping("/delete")
    @Loggable(actionType = "删除 | 用户", actionDetail = "删除信息")
    public boolean delete(Integer id) {
        return userService.removeById(id);
    }

    // 模糊查询用户（按姓名）
    @PostMapping("/listP")
    @Loggable(actionType = "查询 | 用户", actionDetail = "模糊查询信息")
    public Result listP(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(user.getName())) {
            wrapper.like(User::getName, user.getName());
        }
        return Result.suc(userService.list(wrapper));
    }

    // 分页查询（按姓名模糊查）
    @PostMapping("/listPage")
    @Loggable(actionType = "查询 | 用户", actionDetail = "分页模糊查询信息")
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
    @Loggable(actionType = "查询 | 用户", actionDetail = "分页条件模糊查询信息")
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
    @Loggable(actionType = "查询 | 用户", actionDetail = "分页多条件模糊查询信息")
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
        List<User> userList = result.getRecords();

        if (!Objects.equals(roleId, "2")) {
            return Result.suc(result.getRecords(), result.getTotal());
        }
        else{
            // 获取所有教练用户的ID
            List<Integer> coachUserIds = userList.stream()
                    .map(User::getId) // 假设 User 的 id 字段是 userId
                    .collect(Collectors.toList());

            // 批量查询教练表，获取审核状态
            // 假设 Coach 实体中有一个 userId 字段关联 User 表
            LambdaQueryWrapper<coach_users> coachWrapper = new LambdaQueryWrapper<>();
            coachWrapper.in(coach_users::getUserId, coachUserIds);
            List<coach_users> coachList = coachUsersService.list(coachWrapper);

            // 将教练数据（包括审核状态）映射到一个 Map 中，方便查找
            Map<Integer, Integer> auditStatusMap = coachList.stream()
                    .collect(Collectors.toMap(coach_users::getUserId, coach_users::getAuditStatus));

            // 将审核状态添加到 User 对象中
            // 这里我们需要一种方式来添加字段，可以创建一个 DTO 或使用 Map
            List<Map<String, Object>> userMapList = userList.stream().map(user -> {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", user.getId());
                userMap.put("no", user.getNo());
                userMap.put("name", user.getName());
                userMap.put("password", user.getPassword());
                userMap.put("age", user.getAge());
                userMap.put("sex", user.getSex());
                userMap.put("phone", user.getPhone());
                userMap.put("role_id", user.getRoleId());
                userMap.put("isvalid", user.getIsvalid());
                userMap.put("campusId", user.getCampusId());
                userMap.put("balance", user.getBalance());

                // 添加教练的特有字段
                if (auditStatusMap.containsKey(user.getId())) {
                    userMap.put("auditStatus", auditStatusMap.get(user.getId()));
                }
                return userMap;
            }).collect(Collectors.toList());

            // 返回处理后的列表和总数
            return Result.suc(userMapList, result.getTotal());
        }
    }

    // ========== 【学员注册接口】 ==========
    @PostMapping("/registerStudent")
    @Loggable(actionType = "注册 | 用户", actionDetail = "学员注册")
    public Result registerStudent(@RequestBody StudentRegisterDTO studentDTO) {
        // 基础非空校验
        if (StringUtils.isBlank(studentDTO.getNo()) ||
                StringUtils.isBlank(studentDTO.getPassword()) ||
                StringUtils.isBlank(studentDTO.getName()) ||
                StringUtils.isBlank(studentDTO.getPhone()) ||
                StringUtils.isBlank(studentDTO.getStudentNo())) {
            return Result.fail("请填写用户名、密码、姓名、电话和学号");
        }

        // 检查学号是否已存在
        student_users existingStudent = studentUsersService.findByStudentNo(studentDTO.getStudentNo());
        if (existingStudent != null) {
            return Result.fail("学号已存在");
        }

        // 检查用户名是否已存在
        List<User> existingUsers = userService.lambdaQuery().eq(User::getNo, studentDTO.getNo()).list();
        if (!existingUsers.isEmpty()) {
            return Result.fail("用户名已存在");
        }

        // 创建用户基础信息
        User user = new User();
        user.setNo(studentDTO.getNo());
        user.setPassword(studentDTO.getPassword());
        user.setName(studentDTO.getName());
        user.setPhone(studentDTO.getPhone());
        user.setAge(studentDTO.getAge());
        user.setSex("M".equals(studentDTO.getGender()) ? 1 : 0);
        user.setRoleId(3); // 学员角色
        user.setIsvalid("Y");
        user.setCampusId(studentDTO.getCampusId());

        // 保存用户信息
        if (!userService.save(user)) {
            return Result.fail("用户信息保存失败");
        }

        // 创建学员详细信息
        student_users student = new student_users();
        student.setUserId(user.getId());
        student.setName(studentDTO.getName());
        student.setGender(studentDTO.getGender());
        student.setAge(studentDTO.getAge());
        student.setPhone(studentDTO.getPhone());
        student.setStudentNo(studentDTO.getStudentNo());
        student.setClassGrade(studentDTO.getClassGrade());
        student.setTrainingHours(0);
        student.setPaymentStatus(0);
        student.setCampusId(studentDTO.getCampusId());

        // 保存学员详细信息
        if (!studentUsersService.save(student)) {
            // 如果失败，删除已创建的用户记录
            userService.removeById(user.getId());
            return Result.fail("学员信息保存失败");
        }

        return Result.suc("注册成功");
    }

    // ========== 【教练注册接口】 ==========
    @PostMapping("/registerCoach")
    @Loggable(actionType = "注册 | 用户", actionDetail = "教练注册")
    public Result registerCoach(
            @RequestParam("no") String no,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("age") Integer age,
            @RequestParam("sex") Integer sex, // 假设 sex 是 1/0
            @RequestParam("campusId") Integer campusId,
            @RequestParam("roleId") Integer roleId,
            @RequestParam("isvalid") String isvalid,
            @RequestParam("coachNo") String coachNo,
            @RequestParam("level") Integer level,
            @RequestParam("achievements") String achievements,
            @RequestParam(value = "photo", required = false) MultipartFile photo // 接收文件
    ){
        // 基础非空校验
        if (no == null || no.isEmpty() || password == null || password.isEmpty() ||
                name == null || name.isEmpty()  || phone == null || phone.isEmpty() || coachNo == null || coachNo.isEmpty()
            ) {
            return Result.fail("请填写用户名、密码、姓名、电话和教练编号");
        }

        // 检查教练编号是否已存在
        coach_users existingCoach = coachUsersService.findByCoachNo(coachNo);
        if (existingCoach != null) {
            return Result.fail("教练编号已存在");
        }

        // 检查用户名是否已存在
        List<User> existingUsers = userService.lambdaQuery().eq(User::getNo, no).list();
        if (!existingUsers.isEmpty()) {
            return Result.fail("用户名已存在");
        }

        // 创建用户基础信息
        User user = new User();
        user.setNo(no);
        user.setPassword(password);
        user.setName(name);
        user.setPhone(phone);
        user.setAge(age);
        user.setSex("M".equals(sex) ? 1 : 0);
        user.setRoleId(2); // 教练角色
        user.setIsvalid("Y");
        user.setCampusId(campusId);

        // 保存用户信息
        if (!userService.save(user)) {
            return Result.fail("用户信息保存失败");
        }

        // 创建教练详细信息
        coach_users coach = new coach_users();
        coach.setUserId(user.getId());
        coach.setName(name);
        coach.setGender(String.valueOf(sex));
        coach.setAge(age);
        coach.setPhone(phone);
        coach.setCoachNo(coachNo);
        coach.setLevel(level);
        coach.setAchievements(achievements);
        coach.setCampusId(campusId);
        coach.setAuditStatus(0); // 待审核状态

        // 保存教练详细信息
        if (!coachUsersService.save(coach)) {
            // 如果失败，删除已创建的用户记录
            userService.removeById(user.getId());
            return Result.fail("教练信息保存失败");
        }

        return Result.suc("注册成功，等待管理员审核");
    }

    // ========== 【用户充值接口】 ==========
    @PostMapping("/recharge")
    @Loggable(actionType = "充值 | 用户", actionDetail = "用户账户充值")
    public Result recharge(@RequestParam Integer userId, @RequestParam BigDecimal amount) {
        // 参数校验
        if (userId == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return Result.fail("参数错误");
        }

        // 查询用户
        User user = userService.getById(userId);
        if (user == null) {
            return Result.fail("用户不存在");
        }

        // 更新余额
        BigDecimal currentBalance = user.getBalance() != null ? user.getBalance() : BigDecimal.ZERO;
        BigDecimal newBalance = currentBalance.add(amount);
        user.setBalance(newBalance);

        // 保存更新
        if (userService.updateById(user)) {
            Map<String, Object> result = new HashMap<>();
            result.put("balance", newBalance);
            result.put("amount", amount);
            return Result.suc(result, 200L);
        } else {
            return Result.fail("充值失败");
        }
    }
}