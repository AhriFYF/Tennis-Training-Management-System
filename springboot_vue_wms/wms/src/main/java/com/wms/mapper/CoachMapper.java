package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.coach_users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 教练信息Mapper接口
 */
@Mapper
public interface CoachMapper extends BaseMapper<coach_users> {

    /**
     * 根据用户ID查询教练信息
     * @param userId 用户ID
     * @return 教练信息
     */
    @Select("SELECT * FROM coach_users WHERE user_id = #{userId}")
    coach_users selectByUserId(@Param("userId") Integer userId);
}