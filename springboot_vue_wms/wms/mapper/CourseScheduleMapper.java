package com.wms.mapper;

import com.wms.entity.CourseSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface CourseScheduleMapper {
    CourseSchedule selectById(Long id);
    List<CourseSchedule> selectByCoachId(Long coachId);
    List<CourseSchedule> selectAvailableByCoachId(Long coachId);
    List<CourseSchedule> selectByCoachIdAndTimeRange(@Param("coachId") Long coachId,
                                                     @Param("start") LocalDateTime start,
                                                     @Param("end") LocalDateTime end);
    int insert(CourseSchedule schedule);
    int updateAvailability(@Param("id") Long id, @Param("available") Boolean available);
    int delete(Long id);
}
