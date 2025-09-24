package com.wms.mapper.impl;

import com.wms.entity.StudentCourseSelection;
import com.wms.mapper.StudentCourseSelectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentCourseSelectionMapperImpl implements StudentCourseSelectionMapper {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public List<StudentCourseSelection> selectByCoachUserId(Long coachUserId) {
        String sql = "SELECT * FROM student_course_selection WHERE coach_user_id = ?";
        return jdbcTemplate.query(sql, new StudentCourseSelectionRowMapper(), coachUserId);
    }
    
    @Override
    public List<StudentCourseSelection> selectByCoachUserIdAndStatus(Long coachUserId, String status) {
        String sql = "SELECT * FROM student_course_selection WHERE coach_user_id = ? AND status = ?";
        return jdbcTemplate.query(sql, new StudentCourseSelectionRowMapper(), coachUserId, status);
    }
    
    @Override
    public int updateStatus(Long id, String status) {
        String sql = "UPDATE student_course_selection SET status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, status, id);
    }
    
    @Override
    public int insert(StudentCourseSelection selection) {
        String sql = "INSERT INTO student_course_selection (student_id, student_name, coach_user_id, course_name, course_types, parents_phone_number, course_start_time, course_end_time, course_hours, request_time, status, course_information_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, selection.getStudentId(), selection.getStudentName(), selection.getCoachUserId(), selection.getCourseName(), selection.getCourseTypes(), selection.getParentsPhoneNumber(), selection.getCourseStartTime(), selection.getCourseEndTime(), selection.getCourseHours(), selection.getRequestTime(), selection.getStatus(), selection.getCourseInformationId());
    }
    
    @Override
    public StudentCourseSelection selectById(Long id) {
        String sql = "SELECT * FROM student_course_selection WHERE id = ?";
        List<StudentCourseSelection> result = jdbcTemplate.query(sql, new StudentCourseSelectionRowMapper(), id);
        return result.isEmpty() ? null : result.get(0);
    }
    
    private static class StudentCourseSelectionRowMapper implements RowMapper<StudentCourseSelection> {
        @Override
        public StudentCourseSelection mapRow(ResultSet rs, int rowNum) throws SQLException {
            StudentCourseSelection selection = new StudentCourseSelection();
            selection.setId(rs.getLong("id"));
            selection.setStudentId(rs.getLong("student_id"));
            selection.setStudentName(rs.getString("student_name"));
            selection.setCoachUserId(rs.getLong("coach_user_id"));
            selection.setCourseName(rs.getString("course_name"));
            selection.setCourseTypes(rs.getString("course_types"));
            selection.setParentsPhoneNumber(rs.getString("parents_phone_number"));
            selection.setCourseStartTime(rs.getTimestamp("course_start_time").toLocalDateTime());
            selection.setCourseEndTime(rs.getTimestamp("course_end_time").toLocalDateTime());
            selection.setCourseHours(rs.getInt("course_hours"));
            selection.setRequestTime(rs.getTimestamp("request_time").toLocalDateTime());
            selection.setStatus(rs.getString("status"));
            selection.setCourseInformationId(rs.getLong("course_information_id"));
            return selection;
        }
    }
}