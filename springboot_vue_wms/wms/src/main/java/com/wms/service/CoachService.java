package com.wms.service;

import com.wms.entity.coach_users;

/**
 * 教练服务接口
 */
public interface CoachService {

    /**
     * 根据用户ID获取教练信息
     * @param userId 用户ID
     * @return 教练信息
     */
    coach_users getCoachByUserId(Integer userId);

    /**
     * 根据教练ID获取教练信息
     * @param coachId 教练ID
     * @return 教练信息
     */
    coach_users getCoachById(Integer coachId);

    /**
     * 更新教练信息
     * @param coach 教练信息
     * @return 是否成功
     */
    boolean updateCoach(coach_users coach);

    /**
     * 创建教练信息
     * @param coach 教练信息
     * @return 是否成功
     */
    boolean createCoach(coach_users coach);
}