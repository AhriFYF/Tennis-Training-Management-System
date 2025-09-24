package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.student_users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface C0achFindStudentMapper extends BaseMapper<student_users> {

    @Select("SELECT * FROM student_users WHERE student_id IN " +
            "(SELECT student_id FROM coach_selection WHERE coach_id = #{coachId} AND status = '1')")
    List<student_users> findStudentsByCoachId(@Param("coachId") Integer coachId);

    @Select("<script>" +
            "SELECT * FROM student_users WHERE student_id IN " +
            "<foreach collection='studentIds' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    List<student_users> findStudentsByIds(@Param("studentIds") List<Integer> studentIds);
}