package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Loggable;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goods;
import com.wms.entity.Goods;
import com.wms.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/goods")
public class GoodsController {

   @Autowired
    private GoodsService goodsService;
    //新增
    @Loggable(actionType = "新增 | 商品", actionDetail = "新增商品信息")
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        return goodsService.save(goods)?Result.suc():Result.fail();
    }
    //更新
    @Loggable(actionType = "更新 | 商品", actionDetail = "更新商品信息")
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods){
        return goodsService.updateById(goods)?Result.suc():Result.fail();
    }
    //删除
    @Loggable(actionType = "删除 | 商品", actionDetail = "删除商品信息")
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return goodsService.removeById(id)?Result.suc():Result.fail();
    }

    @Loggable(actionType = "查询 | 商品", actionDetail = "分页查询商品信息")
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String goodstype = (String)param.get("goodstype");
        String storage = (String)param.get("storage");

        Page<Goods> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Goods::getName,name);
        }
        if(StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
            lambdaQueryWrapper.eq(Goods::getGoodstype,goodstype);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            lambdaQueryWrapper.eq(Goods::getStorage,storage);
        }

        IPage result = goodsService.pageCC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }
}
