package com.wms.service;

import com.wms.entity.course_information;
import com.wms.mapper.CourseInformationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CourseInformationService {
    
    @Autowired
    private CourseInformationMapper courseInformationMapper;
    
    /**
     * 获取教练上传的所有课程时间
     * @param coachId 教练ID
     * @return 课程时间列表
     */
    public List<course_information> getCoursesByCoachId(Integer coachId) {
        return courseInformationMapper.getCoursesByCoachId(coachId);
    }
    
    /**
     * 获取教练的待确认课程预约请求
     * @param coachId 教练ID
     * @return 待确认的课程预约请求列表
     */
    public List<Map<String, Object>> getPendingCourseRequestsByCoachId(Integer coachId) {
        return courseInformationMapper.getPendingCourseRequestsByCoachId(coachId);
    }
    
    /**
     * 获取教练的已确认课程
     * @param coachId 教练ID
     * @return 已确认的课程列表
     */
    public List<Map<String, Object>> getConfirmedCoursesByCoachId(Integer coachId) {
        return courseInformationMapper.getConfirmedCoursesByCoachId(coachId);
    }
    
    /**
     * 上传新的课程时间
     * @param courseInfo 课程信息
     * @return 是否成功
     */
    public boolean uploadCourseTime(course_information courseInfo) {
        try {
            // 设置默认状态为0（已上传无人预约）
            courseInfo.setStatus("0");
            int result = courseInformationMapper.insert(courseInfo);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 处理课程预约请求（确认或拒绝）
     * @param courseId 课程ID
     * @param status 新状态（2-已确认，0-拒绝）
     * @return 是否成功
     */
    public boolean processCourseRequest(Integer courseId, String status) {
        try {
            int result = courseInformationMapper.updateCourseStatus(courseId, status);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 删除课程时间
     * @param courseId 课程ID
     * @return 是否成功
     */
    public boolean deleteCourseTime(Integer courseId) {
        try {
            int result = courseInformationMapper.deleteById(courseId);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 检查时间冲突
     * @param coachId 教练ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 是否有冲突
     */
    public boolean checkTimeConflict(Integer coachId, LocalDateTime startTime, LocalDateTime endTime) {
        try {
            List<course_information> conflicts = courseInformationMapper.getCoursesByTimeRange(coachId, startTime, endTime);
            return !conflicts.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return true; // 出错时返回true，避免重复添加
        }
    }
}
