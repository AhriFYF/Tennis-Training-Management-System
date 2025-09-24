package com.wms.service;

import com.wms.entity.student_course_selection;
import com.wms.mapper.StudentCourseSelectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentCourseSelectionService {

    @Autowired
    private StudentCourseSelectionMapper studentCourseSelectionMapper;

    public List<student_course_selection> getSelectionsByCoachUserId(Long coachUserId) {
        return studentCourseSelectionMapper.selectByCoachUserId(coachUserId);
    }

    public List<student_course_selection> getSelectionsByCoachUserIdAndStatus(Long coachUserId, String status) {
        return studentCourseSelectionMapper.selectByCoachUserIdAndStatus(coachUserId, status);
    }

    public int updateSelectionStatus(Long id, String status) {
        return studentCourseSelectionMapper.updateStatus(id, status);
    }

    public int insertSelection(student_course_selection selection) {
        return studentCourseSelectionMapper.insert(selection);
    }

    public student_course_selection getSelectionById(Long id) {
        return studentCourseSelectionMapper.selectById(id);
    }
}
