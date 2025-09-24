package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.coach_selection;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CoachFindStudentMapper extends BaseMapper<coach_selection> {

    @Select("SELECT student_id FROM coach_selection WHERE coach_id = #{coachId} AND status = '1'")
    List<Integer> findStudentIdsByCoachId(@Param("coachId") Integer coachId);
}