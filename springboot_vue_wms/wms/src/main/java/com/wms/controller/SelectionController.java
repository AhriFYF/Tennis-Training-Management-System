package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.student_course_selection;
import com.wms.service.SelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/selection")
public class SelectionController {

    @Autowired
    private SelectionService SelectionService; // 注入课程信息Service

    @GetMapping("/list")
    public List<student_course_selection> list(){
        return SelectionService.list();
    }

    // 根据ID查找（示例）
    @GetMapping("/findById")
    public Result findById(@RequestParam Integer id){
        List<student_course_selection> list = SelectionService.lambdaQuery().eq(student_course_selection::getStudentCourseSelectionId, id).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    // 新增
    @PostMapping("/save")
    public Result save(@RequestBody student_course_selection courseInfo){
        return SelectionService.save(courseInfo) ? Result.suc() : Result.fail();
    }

    // 更新
    @PostMapping("/update")
    public Result update(@RequestBody student_course_selection courseInfo){
        return SelectionService.updateById(courseInfo) ? Result.suc() : Result.fail();
    }

    // 删除
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
        return SelectionService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 模糊查询和分页
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String courseName = (String) param.get("courseName");
        String courseTypes = (String) param.get("courseTypes");
        String name = (String) param.get("name");

        Page<student_course_selection> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<student_course_selection> lambdaQueryWrapper = new LambdaQueryWrapper();

        // 根据课程名称进行模糊查询
        if(StringUtils.isNotBlank(courseName) && !"null".equals(courseName)){
            lambdaQueryWrapper.like(student_course_selection::getCourseName, courseName);
        }

        // 根据课程类型进行精确查询
        if(StringUtils.isNotBlank(courseTypes) && !"null".equals(courseTypes)){
            lambdaQueryWrapper.eq(student_course_selection::getCourseTypes, courseTypes);
        }

        // 根据学生姓名进行模糊查询
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(student_course_selection::getName, name);
        }

        IPage<student_course_selection> result = SelectionService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }
}

