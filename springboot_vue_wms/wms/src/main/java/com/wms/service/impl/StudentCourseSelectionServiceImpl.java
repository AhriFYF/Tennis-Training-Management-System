package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.Booking;
import com.wms.entity.student_course_selection;
import com.wms.mapper.StudentCourseSelectionMapper;
import com.wms.service.StudentCourseSelectionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentCourseSelectionServiceImpl extends ServiceImpl<StudentCourseSelectionMapper, student_course_selection> implements StudentCourseSelectionService {

    @Autowired
    private StudentCourseSelectionMapper studentCourseSelectionMapper;

    @Override
    public List<student_course_selection> getByStudentId(Integer studentId) {
        QueryWrapper<student_course_selection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_users", studentId);
        queryWrapper.orderByDesc("create_time");
        return studentCourseSelectionMapper.selectList(queryWrapper);
    }

    @Override
    public List<student_course_selection> getByCoachId(Integer coachId) {
        QueryWrapper<student_course_selection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("coach_user", coachId);
        queryWrapper.orderByDesc("create_time");
        return studentCourseSelectionMapper.selectList(queryWrapper);
    }

    @Override
    public List<student_course_selection> getByCourseNumber(String courseNumber) {
        QueryWrapper<student_course_selection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_number", courseNumber);
        queryWrapper.orderByDesc("create_time");
        return studentCourseSelectionMapper.selectList(queryWrapper);
    }

    @Override
    public List<student_course_selection> getByStatus(String status) {
        QueryWrapper<student_course_selection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", status);
        queryWrapper.orderByDesc("create_time");
        return studentCourseSelectionMapper.selectList(queryWrapper);
    }

    @Override
    public boolean updateStatus(Integer selectionId, String status) {
        student_course_selection selection = studentCourseSelectionMapper.selectById(selectionId);
        if (selection == null) {
            return false;
        }

        selection.setStatus(status);
        selection.setUpdateTime(LocalDateTime.now());
        return studentCourseSelectionMapper.updateById(selection) > 0;
    }

    @Override
    public student_course_selection getBySource(Integer sourceId, String sourceTable) {
        QueryWrapper<student_course_selection> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("source_id", sourceId);
        queryWrapper.eq("source_table", sourceTable);
        return studentCourseSelectionMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional
    public student_course_selection createFromBooking(Booking booking) {
        student_course_selection selection = new student_course_selection();

        // 从预约信息复制基本信息
        selection.setCourseName(booking.getCourseName());
        selection.setCourseNumber(booking.getCourseNumber());
        selection.setCoachUser(booking.getCoachId());
        selection.setStudentUsers(booking.getStudentId());
        selection.setCampusId(booking.getCampusId());

        // 设置默认值
        selection.setStatus("0"); // 0:未确认
        selection.setCreateTime(LocalDateTime.now());
        selection.setUpdateTime(LocalDateTime.now());
        selection.setSourceTable("bookings");
        selection.setSourceId(booking.getBookingId().intValue());
        selection.setSourceUserId(booking.getStudentId());

        // 保存到数据库
        studentCourseSelectionMapper.insert(selection);

        return selection;
    }
}