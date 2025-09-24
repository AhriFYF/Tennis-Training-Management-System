package com.wms.service.impl;

import com.wms.entity.match_registration;
import com.wms.entity.match_schedule;
import com.wms.service.SchedulingService;
import com.wms.common.Result;
import com.wms.service.MatchRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SchedulingServiceImpl implements SchedulingService {
    
    @Autowired
    private MatchRegistrationService matchRegistrationService;
    
    @Override
    public Result generateScheduleForGroup(String groupType) {
        try {
            // 获取该组别所有已支付的报名选手
            List<match_registration> players = matchRegistrationService.lambdaQuery()
                    .eq(match_registration::getGroupType, groupType)
                    .eq(match_registration::getPaymentStatus, "已支付")
                    .list();
            
            if (players.isEmpty()) {
                return Result.fail("该组别暂无已支付报名的选手");
            }
            
            // 生成赛程
            List<match_schedule> scheduleList;
            if (players.size() <= 6) {
                // 小于等于6人，采用全循环赛制
                scheduleList = generateRoundRobinSchedule(players, groupType, 4); // 假设最多4个球台
            } else {
                // 大于6人，需要分组后交叉淘汰，这里简化处理，仍使用循环赛制
                scheduleList = generateRoundRobinSchedule(players, groupType, 4); // 假设最多4个球台
            }
            
            return Result.suc(scheduleList);
        } catch (Exception e) {
            return Result.fail("生成赛程失败: " + e.getMessage());
        }
    }
    
    @Override
    public List<match_schedule> generateRoundRobinSchedule(List<match_registration> players, String groupType, int maxCourts) {
        List<match_schedule> scheduleList = new ArrayList<>();
        int n = players.size();
        
        if (n < 2) {
            return scheduleList; // 至少需要2人才能比赛
        }
        
        // 生成所有可能的对战组合
        List<int[]> matches = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                matches.add(new int[]{i, j});
            }
        }
        
        // 计算总轮数
        int totalRounds = n % 2 == 0 ? n - 1 : n;
        
        // 为每轮分配比赛
        for (int round = 1; round <= totalRounds; round++) {
            List<int[]> roundMatches = getMatchesForRound(matches, n, round);
            int courtNumber = 1;
            
            for (int[] match : roundMatches) {
                if (courtNumber > maxCourts) {
                    break; // 球台已满
                }
                
                match_schedule schedule = new match_schedule();
                schedule.setGroupType(groupType);
                schedule.setMatchRound(round);
                schedule.setPlayer1Id(players.get(match[0]).getStudentId());
                schedule.setPlayer2Id(players.get(match[1]).getStudentId());
                schedule.setCourtNumber(courtNumber);
                // 设置比赛时间，这里简化处理，实际应该根据场地可用时间安排
                schedule.setMatchTime(LocalDateTime.now().plusDays(round));
                scheduleList.add(schedule);
                
                courtNumber++;
            }
        }
        
        return scheduleList;
    }
    
    /**
     * 获取指定轮次的比赛对阵
     */
    private List<int[]> getMatchesForRound(List<int[]> matches, int n, int round) {
        List<int[]> roundMatches = new ArrayList<>();
        Set<Integer> usedPlayers = new HashSet<>();
        
        for (int[] match : matches) {
            int player1 = match[0];
            int player2 = match[1];
            
            // 确保选手在本轮未被安排
            if (!usedPlayers.contains(player1) && !usedPlayers.contains(player2)) {
                roundMatches.add(match);
                usedPlayers.add(player1);
                usedPlayers.add(player2);
            }
            
            // 如果本轮已安排了足够的比赛（n/2场），则停止
            if (roundMatches.size() >= n / 2) {
                break;
            }
        }
        
        return roundMatches;
    }
    
    @Override
    public List<match_schedule> generateKnockoutSchedule(List<match_registration> players, String groupType, int maxCourts) {
        // 淘汰赛赛程生成逻辑
        // 这里简化处理，实际应该根据小组前两名进行交叉淘汰
        return generateRoundRobinSchedule(players, groupType, maxCourts);
    }
}