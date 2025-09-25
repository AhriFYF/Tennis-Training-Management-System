package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.student_course_selection;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentCourseSelectionMapper extends BaseMapper<student_course_selection> {

    @Select("SELECT * FROM student_course_selection WHERE student_users = #{studentId} ORDER BY create_time DESC")
    List<student_course_selection> selectByStudentId(@Param("studentId") Integer studentId);

    @Select("SELECT COUNT(*) FROM student_course_selection WHERE student_users = #{studentId} AND source_id = #{courseId} AND status != '2'")
    int countByStudentAndCourse(@Param("studentId") Integer studentId, @Param("courseId") Integer courseId);
}