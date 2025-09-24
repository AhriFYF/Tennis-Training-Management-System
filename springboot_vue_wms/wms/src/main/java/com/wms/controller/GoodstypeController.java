package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Loggable;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goodstype;
import com.wms.entity.Goodstype;
import com.wms.service.GoodstypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/goodstype")
public class GoodstypeController {
    @Autowired
    private GoodstypeService goodstypeService;
    //新增
    @Loggable(actionType = "新增 | 商品类型", actionDetail = "新增商品类型信息")
    @PostMapping("/save")
    public Result save(@RequestBody Goodstype goodstype){
        return goodstypeService.save(goodstype)?Result.suc():Result.fail();
    }
    //更新
    @Loggable(actionType = "更新 | 商品类型", actionDetail = "更新商品类型信息")
    @PostMapping("/update")
    public Result update(@RequestBody Goodstype goodstype){
        return goodstypeService.updateById(goodstype)?Result.suc():Result.fail();
    }
    //删除
    @Loggable(actionType = "删除 | 商品类型", actionDetail = "删除商品类型信息")
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return goodstypeService.removeById(id)?Result.suc():Result.fail();
    }

    @Loggable(actionType = "查询 | 商品类型", actionDetail = "分页查询商品类型信息")
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");

        Page<Goodstype> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goodstype> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Goodstype::getName,name);
        }

        IPage result = goodstypeService.pageCC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }
    
    @Loggable(actionType = "获取 | 商品类型", actionDetail = "获取所有商品类型信息")
    @GetMapping("/list")
    public Result list(){
        List list = goodstypeService.list();
        return Result.suc(list);
    }
}
