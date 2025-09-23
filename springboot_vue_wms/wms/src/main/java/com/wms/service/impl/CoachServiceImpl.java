package com.wms.service.impl;

import com.wms.entity.coach_users;
import com.wms.mapper.CoachMapper;
import com.wms.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 教练服务实现类
 */
@Service
@Transactional
public class CoachServiceImpl implements CoachService {

    @Autowired
    private CoachMapper coachMapper;

    @Override
    public coach_users getCoachByUserId(Integer userId) {
        return coachMapper.selectByUserId(userId);
    }



    @Override
    public coach_users getCoachById(Integer coachId) {
        return coachMapper.selectById(coachId);
    }

    @Override
    public boolean updateCoach(coach_users coach) {
        // 检查教练是否存在
        coach_users existingCoach = coachMapper.selectById(coach.getCoachId());
        if (existingCoach == null) {
            return false;
        }

        // 更新教练信息
        int result = coachMapper.updateById(coach);
        return result > 0;
    }

    @Override
    public boolean createCoach(coach_users coach) {
        // 检查是否已存在相同用户ID的教练
        coach_users existingCoach = coachMapper.selectByUserId(coach.getUserId());
        if (existingCoach != null) {
            return false;
        }

        // 创建新的教练记录
        int result = coachMapper.insert(coach);
        return result > 0;
    }
}