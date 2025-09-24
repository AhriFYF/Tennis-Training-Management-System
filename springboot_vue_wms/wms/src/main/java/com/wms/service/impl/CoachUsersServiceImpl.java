package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.coach_users;
import com.wms.mapper.CoachUsersMapper;
import com.wms.service.CoachUsersService;
import org.springframework.stereotype.Service;

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
}