package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Loggable;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.User;
import com.wms.entity.course_information;
import com.wms.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService; // 注入课程信息Service

    @GetMapping("/list")
    @Loggable(actionType = "获取 | 课程", actionDetail = "访问数据库获取信息")
    public List<course_information> list(){
        return courseService.list();
    }

    // 根据ID查找（示例）
    @GetMapping("/findById")
    @Loggable(actionType = "查询 | 课程", actionDetail = "访问数据库查询信息")
    public Result findById(@RequestParam Integer id){
        List<course_information> list = courseService.lambdaQuery().eq(course_information::getCourseInformationId, id).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    // 新增
    @PostMapping("/save")
    @Loggable(actionType = "新增 | 课程", actionDetail = "新增信息")
    public Result save(@RequestBody course_information courseInfo){
        return courseService.save(courseInfo) ? Result.suc() : Result.fail();
    }

    // 更新
    @PostMapping("/update")
    @Loggable(actionType = "更新 | 课程", actionDetail = "更新信息")
    public Result update(@RequestBody course_information courseInfo){
        return courseService.updateById(courseInfo) ? Result.suc() : Result.fail();
    }

    // 删除
    @GetMapping("/del")
    @Loggable(actionType = "删除 | 课程", actionDetail = "删除信息")
    public Result del(@RequestParam Integer id){
        return courseService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 模糊查询和分页
    @PostMapping("/listPageC1")
    @Loggable(actionType = "查询 | 课程", actionDetail = "分页多条件模糊查询信息")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String courseName = (String) param.get("courseName");
        String courseTypes = (String) param.get("courseTypes");
        String campusId = String.valueOf(param.get("campusId"));

        Page<course_information> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<course_information> lambdaQueryWrapper = new LambdaQueryWrapper();

        // 根据课程名称进行模糊查询
        if(StringUtils.isNotBlank(courseName) && !"null".equals(courseName)){
            lambdaQueryWrapper.like(course_information::getCourseName, courseName);
        }

        // 根据课程类型进行精确查询
        if(StringUtils.isNotBlank(courseTypes) && !"null".equals(courseTypes)){
            lambdaQueryWrapper.eq(course_information::getCourseTypes, courseTypes);
        }

        System.out.println("查询用户的campusId为" + campusId);
        if(!Objects.equals(campusId, "0")) {
            if (StringUtils.isNotBlank(campusId)) {
                lambdaQueryWrapper.eq(course_information::getCampusId, campusId);
            }
        }

        IPage<course_information> result = courseService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }
}

