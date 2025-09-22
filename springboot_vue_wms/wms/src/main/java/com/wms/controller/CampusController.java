package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Campus;
import com.wms.service.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/campus")
public class CampusController {

    @Autowired
    private CampusService CampusService; // 注入课程信息Service

    @GetMapping("/list")
    public List<Campus> list(){
        return CampusService.list();
    }

    // 根据ID查找（示例）
    @GetMapping("/findById")
    public Result findById(@RequestParam Integer id){
        List<Campus> list = CampusService.lambdaQuery().eq(Campus::getId, id).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    // 新增
    @PostMapping("/save")
    public Result save(@RequestBody Campus courseInfo){
        return CampusService.save(courseInfo) ? Result.suc() : Result.fail();
    }

    // 更新
    @PostMapping("/update")
    public Result update(@RequestBody Campus courseInfo){
        return CampusService.updateById(courseInfo) ? Result.suc() : Result.fail();
    }

    // 删除
    @GetMapping("/del")
    public Result del(@RequestParam Integer id){
        return CampusService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 模糊查询和分页
    @PostMapping("/listPageC1")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();

        String name = (String) param.get("name");
        String contactPerson = (String) param.get("contactPerson");

        Page<Campus> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Campus> lambdaQueryWrapper = new LambdaQueryWrapper();

        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Campus::getName, name);
        }

        if(StringUtils.isNotBlank(contactPerson) && !"null".equals(contactPerson)){
            lambdaQueryWrapper.like(Campus::getContactPerson, contactPerson);
        }

        IPage<Campus> result = CampusService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }
}