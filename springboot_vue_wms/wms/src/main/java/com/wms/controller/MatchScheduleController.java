package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Loggable;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.match_schedule;
import com.wms.service.MatchScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/matchSchedule")
public class MatchScheduleController {

    @Autowired
    private MatchScheduleService MatchScheduleService; // 注入课程信息Service

    @GetMapping("/list")
    @Loggable(actionType = "获取 | 校区", actionDetail = "访问数据库获取信息")
    public List<match_schedule> list(){
        return MatchScheduleService.list();
    }

    // 根据ID查找（示例）
    @GetMapping("/findById")
    @Loggable(actionType = "查询 | 校区", actionDetail = "访问数据库查询信息")
    public Result findById(@RequestParam Integer id){
        List<match_schedule> list = MatchScheduleService.lambdaQuery().eq(match_schedule::getId, id).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    // 新增
    @PostMapping("/save")
    @Loggable(actionType = "新增 | 校区", actionDetail = "新增信息")
    public Result save(@RequestBody match_schedule courseInfo){
        return MatchScheduleService.save(courseInfo) ? Result.suc() : Result.fail();
    }

    // 更新
    @PostMapping("/update")
    @Loggable(actionType = "更新 | 校区", actionDetail = "更新信息")
    public Result update(@RequestBody match_schedule courseInfo){
        return MatchScheduleService.updateById(courseInfo) ? Result.suc() : Result.fail();
    }

    // 删除
    @GetMapping("/del")
    @Loggable(actionType = "删除 | 校区", actionDetail = "删除信息")
    public Result del(@RequestParam Integer id){
        return MatchScheduleService.removeById(id) ? Result.suc() : Result.fail();
    }

    // 模糊查询和分页
    @PostMapping("/listPageC1")
    @Loggable(actionType = "查询 | 校区", actionDetail = "分页多条件模糊查询信息")
    public Result listPageC1(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();

        String groupType = (String) param.get("groupType");
        String player1Id = (String) param.get("player1Id");
        String player2Id = (String) param.get("player2Id");

        Page<match_schedule> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<match_schedule> lambdaQueryWrapper = new LambdaQueryWrapper();

        if(StringUtils.isNotBlank(groupType) && !"null".equals(groupType)){
            lambdaQueryWrapper.like(match_schedule::getGroupType, groupType);
        }

        if(StringUtils.isNotBlank(player1Id) && !"null".equals(player1Id)){
            lambdaQueryWrapper.like(match_schedule::getPlayer1Id, player1Id);
        }

        if(StringUtils.isNotBlank(player2Id) && !"null".equals(player2Id)){
            lambdaQueryWrapper.like(match_schedule::getPlayer2Id, player2Id);
        }

        IPage<match_schedule> result = MatchScheduleService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }
}