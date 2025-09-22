package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.coach_scheduling;
import com.wms.entity.student_course_selection;
import com.wms.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService ScheduleService; // 注入课程信息Service

    @GetMapping("/list")
    public List<coach_scheduling> list(){
        return ScheduleService.list();
    }

    // 根据ID查找（示例）
    @GetMapping("/findById")
    public Result findById(@RequestParam Integer id){
        List<coach_scheduling> list = ScheduleService.lambdaQuery().eq(coach_scheduling::getCoachSchedulingId, id).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    // 新增
    @PostMapping("/save")
    public Result save(@RequestBody coach_scheduling courseInfo){
        return ScheduleService.save(courseInfo) ? Result.suc() : Result.fail();
    }

    // 更新
    @PostMapping("/update")
    public Result update(@RequestBody coach_scheduling courseInfo){
        return ScheduleService.updateById(courseInfo) ? Result.suc() : Result.fail();
    }

    // 删除
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
        return ScheduleService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 模糊查询和分页
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String courseName = (String) param.get("courseName");
        String name = (String) param.get("name");

        Page<coach_scheduling> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<coach_scheduling> lambdaQueryWrapper = new LambdaQueryWrapper();

        // 根据课程名称进行模糊查询
        if(StringUtils.isNotBlank(courseName) && !"null".equals(courseName)){
            lambdaQueryWrapper.like(coach_scheduling::getCourseName, courseName);
        }

        // 根据学生姓名进行模糊查询
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(coach_scheduling::getName, name);
        }

        IPage<coach_scheduling> result = ScheduleService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }
}

