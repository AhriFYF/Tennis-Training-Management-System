package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.teaching_evaluation;
import com.wms.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService EvaluationService; // 注入课程信息Service

    @GetMapping("/list")
    public List<teaching_evaluation> list(){
        return EvaluationService.list();
    }

    // 根据ID查找（示例）
    @GetMapping("/findById")
    public Result findById(@RequestParam Integer id){
        List<teaching_evaluation> list = EvaluationService.lambdaQuery().eq(teaching_evaluation::getTeachingEvaluationId, id).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    // 新增
    @PostMapping("/save")
    public Result save(@RequestBody teaching_evaluation courseInfo){
        return EvaluationService.save(courseInfo) ? Result.suc() : Result.fail();
    }

    // 更新
    @PostMapping("/update")
    public Result update(@RequestBody teaching_evaluation courseInfo){
        return EvaluationService.updateById(courseInfo) ? Result.suc() : Result.fail();
    }

    // 删除
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
        return EvaluationService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 模糊查询和分页
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String courseName = (String) param.get("courseName");

        Page<teaching_evaluation> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<teaching_evaluation> lambdaQueryWrapper = new LambdaQueryWrapper();

        // 根据课程名称进行模糊查询
        if(StringUtils.isNotBlank(courseName) && !"null".equals(courseName)){
            lambdaQueryWrapper.like(teaching_evaluation::getCourseName, courseName);
        }

        IPage<teaching_evaluation> result = EvaluationService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }
}