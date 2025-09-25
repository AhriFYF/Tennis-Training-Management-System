package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.coach_users;
import com.wms.mapper.CoachUsersMapper;
import com.wms.service.CoachUsersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachUsersServiceImpl extends ServiceImpl<CoachUsersMapper, coach_users> implements CoachUsersService {

    @Override
    public coach_users findByUserId(Integer userId) {
        LambdaQueryWrapper<coach_users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(coach_users::getUserId, userId);
        return this.getOne(wrapper);
    }

    @Override
    public coach_users findByCoachNo(String coachNo) {
        LambdaQueryWrapper<coach_users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(coach_users::getCoachNo, coachNo);
        return this.getOne(wrapper);
    }

    @Override
    public coach_users findByCoachId(Integer coachId) {
        LambdaQueryWrapper<coach_users> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(coach_users::getCoachId, coachId);
        return this.getOne(wrapper);
    }

    @Override
    public List<coach_users> searchCoaches(coach_users searchCondition) {
        LambdaQueryWrapper<coach_users> wrapper = Wrappers.lambdaQuery();
        
        // 根据姓名搜索（模糊查询）
        if (searchCondition.getName() != null && !searchCondition.getName().isEmpty()) {
            wrapper.like(coach_users::getName, searchCondition.getName());
        }
        
        // 根据性别搜索
        if (searchCondition.getGender() != null && !searchCondition.getGender().isEmpty()) {
            wrapper.eq(coach_users::getGender, searchCondition.getGender());
        }
        
        // 只查询审核通过的教练
        wrapper.eq(coach_users::getAuditStatus, 1);
        
        return this.list(wrapper);
    }

    @Override
    public List<coach_users> getMyCoaches(Integer studentId) {
        return baseMapper.getMyCoaches(studentId);
    }
}