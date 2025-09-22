package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.course_information;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface CourseInformationMapper extends BaseMapper<course_information> {
    
    /**
     * 根据教练ID获取该教练上传的所有课程时间
     * @param coachId 教练ID
     * @return 课程时间列表
     */
    @Select("SELECT * FROM course_information WHERE coach_user = #{coachId} ORDER BY course_start_time ASC")
    List<course_information> getCoursesByCoachId(@Param("coachId") Integer coachId);
    
    /**
     * 根据教练ID获取待确认的课程预约请求
     * @param coachId 教练ID
     * @return 待确认的课程预约请求列表
     */
    @Select("SELECT ci.*, scs.student_course_selection_id, scs.name as studentName, scs.student_users, " +
            "scs.parents_phone_number, scs.create_time as requestTime " +
            "FROM course_information ci " +
            "LEFT JOIN student_course_selection scs ON ci.course_information_id = scs.source_id " +
            "WHERE ci.coach_user = #{coachId} AND ci.status = '1' " +
            "ORDER BY scs.create_time ASC")
    List<Map<String, Object>> getPendingCourseRequestsByCoachId(@Param("coachId") Integer coachId);
    
    /**
     * 根据教练ID获取已确认的课程
     * @param coachId 教练ID
     * @return 已确认的课程列表
     */
    @Select("SELECT ci.*, scs.student_course_selection_id, scs.name as studentName, scs.student_users, " +
            "scs.parents_phone_number, scs.create_time as requestTime " +
            "FROM course_information ci " +
            "LEFT JOIN student_course_selection scs ON ci.course_information_id = scs.source_id " +
            "WHERE ci.coach_user = #{coachId} AND ci.status = '2' " +
            "ORDER BY ci.course_start_time ASC")
    List<Map<String, Object>> getConfirmedCoursesByCoachId(@Param("coachId") Integer coachId);
    
    /**
     * 更新课程状态
     * @param courseId 课程ID
     * @param status 新状态
     * @return 影响行数
     */
    @Update("UPDATE course_information SET status = #{status} WHERE course_information_id = #{courseId}")
    int updateCourseStatus(@Param("courseId") Integer courseId, @Param("status") String status);
    
    /**
     * 根据时间范围获取教练的课程
     * @param coachId 教练ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 课程列表
     */
    @Select("SELECT * FROM course_information " +
            "WHERE coach_user = #{coachId} " +
            "AND course_start_time >= #{startTime} " +
            "AND course_end_time <= #{endTime} " +
            "ORDER BY course_start_time ASC")
    List<course_information> getCoursesByTimeRange(@Param("coachId") Integer coachId, 
                                                   @Param("startTime") LocalDateTime startTime, 
                                                   @Param("endTime") LocalDateTime endTime);
}
