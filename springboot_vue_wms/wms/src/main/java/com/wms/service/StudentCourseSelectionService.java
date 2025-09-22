package com.wms.service;

import com.wms.entity.student_course_selection;
import com.wms.mapper.StudentCourseSelectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentCourseSelectionService {
    
    @Autowired
    private StudentCourseSelectionMapper studentCourseSelectionMapper;
    
    /**
     * 获取课程的所有选课记录
     * @param courseId 课程ID
     * @return 选课记录列表
     */
    public List<student_course_selection> getSelectionsByCourseId(Integer courseId) {
        return studentCourseSelectionMapper.getSelectionsByCourseId(courseId);
    }
    
    /**
     * 获取教练的所有学员选课记录
     * @param coachId 教练ID
     * @return 选课记录列表
     */
    public List<Map<String, Object>> getSelectionsByCoachId(Integer coachId) {
        return studentCourseSelectionMapper.getSelectionsByCoachId(coachId);
    }
    
    /**
     * 获取学员的所有选课记录
     * @param studentId 学员ID
     * @return 选课记录列表
     */
    public List<Map<String, Object>> getSelectionsByStudentId(Integer studentId) {
        return studentCourseSelectionMapper.getSelectionsByStudentId(studentId);
    }
    
    /**
     * 更新选课记录状态
     * @param selectionId 选课记录ID
     * @param status 新状态
     * @return 是否成功
     */
    public boolean updateSelectionStatus(Integer selectionId, String status) {
        try {
            int result = studentCourseSelectionMapper.updateSelectionStatus(selectionId, status);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 获取教练和学员的选课记录
     * @param coachId 教练ID
     * @param studentId 学员ID
     * @return 选课记录列表
     */
    public List<Map<String, Object>> getSelectionsByCoachAndStudent(Integer coachId, Integer studentId) {
        return studentCourseSelectionMapper.getSelectionsByCoachAndStudent(coachId, studentId);
    }
    
    /**
     * 删除选课记录
     * @param selectionId 选课记录ID
     * @return 是否成功
     */
    public boolean deleteSelection(Integer selectionId) {
        try {
            int result = studentCourseSelectionMapper.deleteById(selectionId);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
