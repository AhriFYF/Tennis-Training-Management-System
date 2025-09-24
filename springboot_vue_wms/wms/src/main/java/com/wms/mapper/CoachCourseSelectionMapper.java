package com.wms.mapper;

import com.wms.entity.student_course_selection;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CoachCourseSelectionMapper {

    @Select("SELECT * FROM student_course_selection WHERE coach_user = #{coachUser}")
    List<student_course_selection> selectByCoachUserId(@Param("coachUser") Integer coachUser);

    @Select("SELECT * FROM student_course_selection WHERE coach_user = #{coachUser} AND status = #{status}")
    List<student_course_selection> selectByCoachUserIdAndStatus(@Param("coachUser") Integer coachUser, @Param("status") String status);

    @Update("UPDATE student_course_selection SET status = #{status} WHERE student_course_selection_id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") String status);

    @Insert("INSERT INTO student_course_selection(" +
            "course_name, course_number, course_types, coach_user, coach_name, " +
            "course_hours, duration_of_class_hours, course_prices, student_users, " +
            "name, student_age, parents_phone_number, phone_number, pay_state, " +
            "pay_type, coach_scheduling_limit_times, create_time, update_time, " +
            "source_table, source_id, source_user_id, campus_id, status) " +
            "VALUES(#{courseName}, #{courseNumber}, #{courseTypes}, #{coachUser}, #{coachName}, " +
            "#{courseHours}, #{durationOfClassHours}, #{coursePrices}, #{studentUsers}, " +
            "#{name}, #{studentAge}, #{parentsPhoneNumber}, #{phoneNumber}, #{payState}, " +
            "#{payType}, #{coachSchedulingLimitTimes}, #{createTime}, #{updateTime}, " +
            "#{sourceTable}, #{sourceId}, #{sourceUserId}, #{campusId}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "studentCourseSelectionId")
    int insert(student_course_selection selection);

    @Select("SELECT * FROM student_course_selection WHERE student_course_selection_id = #{id}")
    student_course_selection selectById(Integer id);
}