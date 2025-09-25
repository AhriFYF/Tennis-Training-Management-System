package com.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.coach_users;

import java.util.List;

public interface CoachUsersService extends IService<coach_users> {
    /**
     * 根据用户ID查找教练信息
     */
    coach_users findByUserId(Integer userId);

    /**
     * 根据教练编号查找教练信息
     */
    coach_users findByCoachNo(String coachNo);

    /**
     * 根据教练ID查找教练信息
     */
    coach_users findByCoachId(Integer coachId);

    /**
     * 搜索教练
     */
    List<coach_users> searchCoaches(coach_users searchCondition);

    /**
     * 获取我的教练（已确认的）
     */
    List<coach_users> getMyCoaches(Integer studentId);
}