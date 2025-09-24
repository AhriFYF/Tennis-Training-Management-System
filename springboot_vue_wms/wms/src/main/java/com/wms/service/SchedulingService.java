package com.wms.service;

import com.wms.entity.match_registration;
import com.wms.entity.match_schedule;
import com.wms.common.Result;
import java.util.List;

public interface SchedulingService {
    /**
     * 为指定组别生成比赛日程
     * @param groupType 组别 ('甲', '乙', '丙')
     * @return 排赛结果
     */
    Result generateScheduleForGroup(String groupType);
    
    /**
     * 生成循环赛程
     * @param players 参赛选手列表
     * @param groupType 组别
     * @param maxCourts 最大球台数
     * @return 比赛日程列表
     */
    List<match_schedule> generateRoundRobinSchedule(List<match_registration> players, String groupType, int maxCourts);
    
    /**
     * 生成淘汰赛程
     * @param players 参赛选手列表
     * @param groupType 组别
     * @param maxCourts 最大球台数
     * @return 比赛日程列表
     */
    List<match_schedule> generateKnockoutSchedule(List<match_registration> players, String groupType, int maxCourts);
}