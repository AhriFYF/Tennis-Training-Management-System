package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.Loggable;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.match_schedule;
import com.wms.entity.match_registration;
import com.wms.service.MatchScheduleService;
import com.wms.service.SchedulingService;
import com.wms.service.MatchRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/matchSchedule")
public class MatchScheduleController {

    @Autowired
    private MatchScheduleService matchScheduleService;
    
    @Autowired
    private SchedulingService schedulingService;
    
    @Autowired
    private MatchRegistrationService matchRegistrationService;

    @GetMapping("/list")
    @Loggable(actionType = "获取 | 月赛赛程", actionDetail = "访问数据库获取月赛赛程信息")
    public List<match_schedule> list(){
        return matchScheduleService.list();
    }

    // 根据ID查找
    @GetMapping("/findById")
    @Loggable(actionType = "查询 | 月赛赛程", actionDetail = "根据ID查询月赛赛程信息")
    public Result findById(@RequestParam Long id){
        match_schedule schedule = matchScheduleService.getById(id);
        return schedule != null ? Result.suc(schedule) : Result.fail("未找到赛程信息");
    }

    // 为指定组别生成赛程
    @PostMapping("/generate/{groupType}")
    @Loggable(actionType = "生成 | 月赛赛程", actionDetail = "为指定组别生成月赛赛程")
    public Result generateSchedule(@PathVariable String groupType) {
        // 检查是否已存在该组别的赛程
        List<match_schedule> existingSchedules = matchScheduleService.lambdaQuery()
                .eq(match_schedule::getGroupType, groupType)
                .list();
        
        if (!existingSchedules.isEmpty()) {
            return Result.fail("该组别赛程已存在，请先删除现有赛程");
        }
        
        // 生成赛程
        Result result = schedulingService.generateScheduleForGroup(groupType);
        if (result.getCode() == 200) {
            // 保存赛程
            List<match_schedule> scheduleList = (List<match_schedule>) result.getData();
            for (match_schedule schedule : scheduleList) {
                matchScheduleService.save(schedule);
            }
            return Result.suc("赛程生成成功");
        } else {
            return result;
        }
    }

    // 更新
    @PostMapping("/update")
    @Loggable(actionType = "更新 | 月赛赛程", actionDetail = "更新月赛赛程信息")
    public Result update(@RequestBody match_schedule schedule){
        return matchScheduleService.updateById(schedule) ? Result.suc() : Result.fail();
    }

    // 删除
    @GetMapping("/del")
    @Loggable(actionType = "删除 | 月赛赛程", actionDetail = "删除月赛赛程信息")
    public Result del(@RequestParam Long id){
        return matchScheduleService.removeById(id) ? Result.suc() : Result.fail();
    }
    
    // 删除指定组别的所有赛程
    @DeleteMapping("/deleteByGroup/{groupType}")
    @Loggable(actionType = "删除 | 月赛赛程", actionDetail = "删除指定组别的所有月赛赛程")
    public Result deleteByGroup(@PathVariable String groupType) {
        // 1. 构建查询条件
        LambdaQueryWrapper<match_schedule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(match_schedule::getGroupType, groupType);

        // 2. 调用 Service 的 remove 方法，传入查询条件
        boolean result = matchScheduleService.remove(queryWrapper);

        // 3. 根据结果返回
        return result ? Result.suc("删除成功") : Result.fail("删除失败");
    }

    // 模糊查询和分页
    @PostMapping("/listPageC1")
    @Loggable(actionType = "查询 | 月赛赛程", actionDetail = "分页多条件模糊查询月赛赛程信息")
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

        IPage<match_schedule> result = matchScheduleService.pageCC(page, lambdaQueryWrapper);

        System.out.println("total==" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }
    
    // 获取指定组别的赛程
    @GetMapping("/byGroup/{groupType}")
    @Loggable(actionType = "查询 | 月赛赛程", actionDetail = "获取指定组别的月赛赛程")
    public Result getByGroup(@PathVariable String groupType) {
        List<match_schedule> schedules = matchScheduleService.lambdaQuery()
                .eq(match_schedule::getGroupType, groupType)
                .orderByAsc(match_schedule::getMatchRound)
                .orderByAsc(match_schedule::getCourtNumber)
                .list();
        return Result.suc(schedules);
    }
    
    // 获取指定选手的赛程
    @GetMapping("/byPlayer")
    @Loggable(actionType = "查询 | 月赛赛程", actionDetail = "获取指定选手的月赛赛程")
    public Result getByPlayer(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return Result.fail("未登录或会话已过期");
        }
        
        List<match_schedule> schedules = matchScheduleService.lambdaQuery()
                .and(wrapper -> wrapper.eq(match_schedule::getPlayer1Id, userId)
                        .or().eq(match_schedule::getPlayer2Id, userId))
                .orderByAsc(match_schedule::getMatchRound)
                .list();
        return Result.suc(schedules);
    }
}