package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.course_information;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseInformationMapper extends BaseMapper<course_information> {

    @Select("SELECT * FROM course_information WHERE coach_user = #{coachUserId} AND status = '0' AND course_start_time > NOW() ORDER BY course_start_time")
    List<course_information> selectAvailableByCoachUserId(@Param("coachUserId") Integer coachUserId);


}