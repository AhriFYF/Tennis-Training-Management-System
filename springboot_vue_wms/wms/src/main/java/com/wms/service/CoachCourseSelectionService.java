package com.wms.service;

import com.wms.entity.student_course_selection;
import com.wms.mapper.CoachCourseSelectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachCourseSelectionService {

    @Autowired
    private CoachCourseSelectionMapper coachCourseSelectionMapper;

    public List<student_course_selection> getSelectionsByCoachUserId(Integer coachUserId) {
        return coachCourseSelectionMapper.selectByCoachUserId(coachUserId);
    }

    public List<student_course_selection> getSelectionsByCoachUserIdAndStatus(Integer coachUserId, String status) {
        return coachCourseSelectionMapper.selectByCoachUserIdAndStatus(coachUserId, status);
    }

    public int updateSelectionStatus(Integer id, String status) {
        return coachCourseSelectionMapper.updateStatus(id, status);
    }

    public int insertSelection(student_course_selection selection) {
        return coachCourseSelectionMapper.insert(selection);
    }

    public student_course_selection getSelectionById(Integer id) {
        return coachCourseSelectionMapper.selectById(id);
    }
}