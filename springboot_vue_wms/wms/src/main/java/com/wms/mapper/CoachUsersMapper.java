package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.coach_users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}