package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Loggable;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.match_registration;
import com.wms.entity.User;
import com.wms.service.MatchRegistrationService;
import com.wms.service.PaymentService;
import com.wms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/matchRegistration")
public class MatchRegistrationController {

    @Autowired
    private MatchRegistrationService matchRegistrationService;
    
    @Autowired
    private PaymentService paymentService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    @Loggable(actionType = "获取 | 月赛报名", actionDetail = "访问数据库获取月赛报名信息")
    public List<match_registration> list(){
        return matchRegistrationService.list();
    }

    // 根据ID查找
    @GetMapping("/findById")
    @Loggable(actionType = "查询 | 月赛报名", actionDetail = "根据ID查询月赛报名信息")
    public Result findById(@RequestParam Long id){
        match_registration registration = matchRegistrationService.getById(id);
        return registration != null ? Result.suc(registration) : Result.fail("未找到报名信息");
    }

    // 学员报名月赛
    @PostMapping("/register")
    @Loggable(actionType = "新增 | 月赛报名", actionDetail = "学员报名月赛并支付报名费")
    public Result register(@RequestBody match_registration registration, HttpSession session) {
        // 从session获取当前用户ID
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail("未登录或会话已过期");
        }
        
        // 检查用户是否为学员
        User user = userService.getById(userId);
        if (user == null || user.getRoleId() != 3) { // 3表示学员角色
            return Result.fail("只有学员可以报名月赛");
        }
        
        // 检查是否已报名相同组别
        List<match_registration> existingRegistrations = matchRegistrationService.lambdaQuery()
                .eq(match_registration::getStudentId, userId)
                .eq(match_registration::getGroupType, registration.getGroupType())
                .list();
        if (!existingRegistrations.isEmpty()) {
            return Result.fail("您已报名该组别");
        }
        
        // 设置报名信息
        registration.setStudentId(userId);
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setRegistrationFee(new BigDecimal("30.00"));
        registration.setPaymentStatus("未支付");
        
        // 保存报名信息
        if (matchRegistrationService.save(registration)) {
            return Result.suc("报名成功，请支付报名费");
        } else {
            return Result.fail("报名失败");
        }
    }

    // 支付报名费
    @PostMapping("/pay/{id}")
    @Loggable(actionType = "支付 | 月赛报名费", actionDetail = "支付月赛报名费")
    public Result payRegistrationFee(@PathVariable Long id, HttpSession session) {
        // 从session获取当前用户ID
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail("未登录或会话已过期");
        }
        
        // 获取报名信息
        match_registration registration = matchRegistrationService.getById(id);
        if (registration == null) {
            return Result.fail("报名信息不存在");
        }
        
        // 检查是否是本人的报名信息
        if (!registration.getStudentId().equals(userId)) {
            return Result.fail("无权操作他人的报名信息");
        }
        
        // 检查是否已支付
        if ("已支付".equals(registration.getPaymentStatus())) {
            return Result.fail("报名费已支付");
        }
        
        // 扣除报名费
        Result paymentResult = paymentService.deductBalance(userId, registration.getRegistrationFee(), "月赛报名费");
        if (paymentResult.getCode() != 200) {
            return paymentResult; // 余额不足等错误信息
        }
        
        // 更新支付状态
        registration.setPaymentStatus("已支付");
        if (matchRegistrationService.updateById(registration)) {
            return Result.suc("支付成功");
        } else {
            return Result.fail("支付失败");
        }
    }

    // 更新
    @PostMapping("/update")
    @Loggable(actionType = "更新 | 月赛报名", actionDetail = "更新月赛报名信息")
    public Result update(@RequestBody match_registration registration){
        return matchRegistrationService.updateById(registration) ? Result.suc() : Result.fail();
    }

    // 删除
    @GetMapping("/del")
    @Loggable(actionType = "删除 | 月赛报名", actionDetail = "删除月赛报名信息")
    public Result del(@RequestParam Long id){
        return matchRegistrationService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 模糊查询和分页
    @PostMapping("/listPageC1")
    @Loggable(actionType = "查询 | 月赛报名", actionDetail = "分页多条件模糊查询月赛报名信息")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();

        String studentId = (String) param.get("studentId");
        String groupType = (String) param.get("groupType");

        Page<match_registration> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<match_registration> lambdaQueryWrapper = new LambdaQueryWrapper();

        if(StringUtils.isNotBlank(studentId) && !"null".equals(studentId)){
            lambdaQueryWrapper.like(match_registration::getStudentId, studentId);
        }

        if(StringUtils.isNotBlank(groupType) && !"null".equals(groupType)){
            lambdaQueryWrapper.like(match_registration::getGroupType, groupType);
        }

        IPage<match_registration> result = matchRegistrationService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }
    
    // 获取当前用户报名信息
    @GetMapping("/myRegistrations")
    @Loggable(actionType = "查询 | 我的月赛报名", actionDetail = "获取当前用户的月赛报名信息")
    public Result getMyRegistrations(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail("未登录或会话已过期");
        }
        
        List<match_registration> registrations = matchRegistrationService.lambdaQuery()
                .eq(match_registration::getStudentId, userId)
                .list();
        return Result.suc(registrations);
    }
}
