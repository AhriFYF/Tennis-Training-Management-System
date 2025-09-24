package com.wms.mapper;

import com.wms.entity.student_course_selection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentCourseSelectionMapper {
    List<student_course_selection> selectByCoachUserId(@Param("coachUserId") Long coachUserId);
    List<student_course_selection> selectByCoachUserIdAndStatus(@Param("coachUserId") Long coachUserId, @Param("status") String status);
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    int insert(student_course_selection selection);
    student_course_selection selectById(Long id);
}