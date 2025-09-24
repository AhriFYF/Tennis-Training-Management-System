package com.wms.mapper;

import com.wms.entity.StudentCourseSelection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentCourseSelectionMapper {
    List<StudentCourseSelection> selectByCoachUserId(@Param("coachUserId") Long coachUserId);
    List<StudentCourseSelection> selectByCoachUserIdAndStatus(@Param("coachUserId") Long coachUserId, @Param("status") String status);
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    int insert(StudentCourseSelection selection);
    StudentCourseSelection selectById(Long id);
}