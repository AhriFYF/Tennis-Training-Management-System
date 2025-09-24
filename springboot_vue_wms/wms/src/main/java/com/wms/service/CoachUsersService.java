package com.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.coach_users;

public interface CoachUsersService extends IService<coach_users> {
    /**
     * 根据用户ID查找教练信息
     */
    coach_users findByUserId(Integer userId);

    /**
     * 根据教练编号查找教练信息
     */
    coach_users findByCoachNo(String coachNo);
}