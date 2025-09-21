package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.coach_cancels_class;
import com.wms.service.CoachCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/coachCancel")
public class CoachCancelController {

    @Autowired
    private CoachCancelService CoachCancelService; // 注入课程信息Service

    @GetMapping("/list")
    public List<coach_cancels_class> list(){
        return CoachCancelService.list();
    }

    // 根据ID查找（示例）
    @GetMapping("/findById")
    public Result findById(@RequestParam Integer id){
        List<coach_cancels_class> list = CoachCancelService.lambdaQuery().eq(coach_cancels_class::getCoachCancelsClassId, id).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    // 新增
    @PostMapping("/save")
    public Result save(@RequestBody coach_cancels_class courseInfo){
        return CoachCancelService.save(courseInfo) ? Result.suc() : Result.fail();
    }

    // 更新
    @PostMapping("/update")
    public Result update(@RequestBody coach_cancels_class courseInfo){
        return CoachCancelService.updateById(courseInfo) ? Result.suc() : Result.fail();
    }

    // 删除
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
        return CoachCancelService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 模糊查询和分页
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String courseName = (String) param.get("courseName");

        Page<coach_cancels_class> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<coach_cancels_class> lambdaQueryWrapper = new LambdaQueryWrapper();

        // 根据课程名称进行模糊查询
        if(StringUtils.isNotBlank(courseName) && !"null".equals(courseName)){
            lambdaQueryWrapper.like(coach_cancels_class::getCourseName, courseName);
        }

        IPage<coach_cancels_class> result = CoachCancelService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }
}