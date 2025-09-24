package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Loggable;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.match_registration;

import com.wms.entity.match_registration;
import com.wms.service.MatchRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/matchRegistration")
public class MatchRegistrationController {

    @Autowired
    private MatchRegistrationService MatchRegistrationService; // 注入课程信息Service

    @GetMapping("/list")
    @Loggable(actionType = "获取 | 校区", actionDetail = "访问数据库获取信息")
    public List<match_registration> list(){
        return MatchRegistrationService.list();
    }

    // 根据ID查找（示例）
    @GetMapping("/findById")
    @Loggable(actionType = "查询 | 校区", actionDetail = "访问数据库查询信息")
    public Result findById(@RequestParam Integer id){
        List<match_registration> list = MatchRegistrationService.lambdaQuery().eq(match_registration::getId, id).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    // 新增
    @PostMapping("/save")
    @Loggable(actionType = "新增 | 校区", actionDetail = "新增信息")
    public Result save(@RequestBody match_registration courseInfo){
        return MatchRegistrationService.save(courseInfo) ? Result.suc() : Result.fail();
    }

    // 更新
    @PostMapping("/update")
    @Loggable(actionType = "更新 | 校区", actionDetail = "更新信息")
    public Result update(@RequestBody match_registration courseInfo){
        return MatchRegistrationService.updateById(courseInfo) ? Result.suc() : Result.fail();
    }

    // 删除
    @GetMapping("/del")
    @Loggable(actionType = "删除 | 校区", actionDetail = "删除信息")
    public Result del(@RequestParam Integer id){
        return MatchRegistrationService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 模糊查询和分页
    @PostMapping("/listPageC1")
    @Loggable(actionType = "查询 | 校区", actionDetail = "分页多条件模糊查询信息")
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

        IPage<match_registration> result = MatchRegistrationService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }
}
