package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.student_course_selection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentCourseSelectionMapper extends BaseMapper<student_course_selection> {
    
    /**
     * 根据课程ID获取该课程的所有选课记录
     * @param courseId 课程ID
     * @return 选课记录列表
     */
    @Select("SELECT * FROM student_course_selection WHERE source_id = #{courseId} ORDER BY create_time ASC")
    List<student_course_selection> getSelectionsByCourseId(@Param("courseId") Integer courseId);
    
    /**
     * 根据教练ID获取该教练的所有学员选课记录
     * @param coachId 教练ID
     * @return 选课记录列表
     */
    @Select("SELECT scs.*, ci.course_name, ci.course_start_time, ci.course_end_time " +
            "FROM student_course_selection scs " +
            "LEFT JOIN course_information ci ON scs.source_id = ci.course_information_id " +
            "WHERE ci.coach_user = #{coachId} " +
            "ORDER BY scs.create_time DESC")
    List<Map<String, Object>> getSelectionsByCoachId(@Param("coachId") Integer coachId);
    
    /**
     * 根据学员ID获取该学员的所有选课记录
     * @param studentId 学员ID
     * @return 选课记录列表
     */
    @Select("SELECT scs.*, ci.course_name, ci.course_start_time, ci.course_end_time, ci.coach_name " +
            "FROM student_course_selection scs " +
            "LEFT JOIN course_information ci ON scs.source_id = ci.course_information_id " +
            "WHERE scs.student_users = #{studentId} " +
            "ORDER BY scs.create_time DESC")
    List<Map<String, Object>> getSelectionsByStudentId(@Param("studentId") Integer studentId);
    
    /**
     * 更新选课记录状态
     * @param selectionId 选课记录ID
     * @param status 新状态
     * @return 影响行数
     */
    @Update("UPDATE student_course_selection SET pay_state = #{status} WHERE student_course_selection_id = #{selectionId}")
    int updateSelectionStatus(@Param("selectionId") Integer selectionId, @Param("status") String status);
    
    /**
     * 根据教练ID和学员ID获取选课记录
     * @param coachId 教练ID
     * @param studentId 学员ID
     * @return 选课记录列表
     */
    @Select("SELECT scs.*, ci.course_name, ci.course_start_time, ci.course_end_time " +
            "FROM student_course_selection scs " +
            "LEFT JOIN course_information ci ON scs.source_id = ci.course_information_id " +
            "WHERE ci.coach_user = #{coachId} AND scs.student_users = #{studentId} " +
            "ORDER BY scs.create_time DESC")
    List<Map<String, Object>> getSelectionsByCoachAndStudent(@Param("coachId") Integer coachId, @Param("studentId") Integer studentId);
}
