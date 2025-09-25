package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.coach_users;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoachUsersMapper extends BaseMapper<coach_users> {
    
    /**
     * 获取我的教练（已确认的）
     */
    @Select("SELECT cu.* FROM coach_users cu " +
            "JOIN coach_selection cs ON cu.user_id = cs.coach_id " +
            "WHERE cs.student_id = #{studentId} AND cs.status = '1'")
    List<coach_users> getMyCoaches(Integer studentId);

    /**
     * 根据教练ID查询教练信息
     */
    @Select("SELECT * FROM coach_users WHERE coach_id = #{coachId}")
    coach_users selectByCoachId(@Param("coachId") Integer coachId);

    /**
     * 根据用户ID查询教练信息
     */
    @Select("SELECT * FROM coach_users WHERE user_id = #{userId}")
    coach_users findByUserId(@Param("userId") Integer userId);
}