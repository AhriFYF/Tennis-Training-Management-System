package com.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.Booking;
import com.wms.entity.student_course_selection;
import java.util.List;

public interface StudentCourseSelectionService extends IService<student_course_selection> {

    /**
     * 根据学生ID查询选课记录
     * @param studentId 学生ID
     * @return 选课记录列表
     */
    List<student_course_selection> getByStudentId(Integer studentId);

    /**
     * 根据教练ID查询选课记录
     * @param coachId 教练ID
     * @return 选课记录列表
     */
    List<student_course_selection> getByCoachId(Integer coachId);

    /**
     * 根据课程编号查询选课记录
     * @param courseNumber 课程编号
     * @return 选课记录列表
     */
    List<student_course_selection> getByCourseNumber(String courseNumber);

    /**
     * 根据状态查询选课记录
     * @param status 状态
     * @return 选课记录列表
     */
    List<student_course_selection> getByStatus(String status);

    /**
     * 更新选课记录状态
     * @param selectionId 选课记录ID
     * @param status 新状态
     * @return 是否成功
     */
    boolean updateStatus(Integer selectionId, String status);

    /**
     * 根据源ID和源表查询选课记录
     * @param sourceId 源ID
     * @param sourceTable 源表
     * @return 选课记录
     */
    student_course_selection getBySource(Integer sourceId, String sourceTable);

    /**
     * 创建选课记录（从预约创建）
     * @param booking 预约信息
     * @return 创建的选课记录
     */
    student_course_selection createFromBooking(Booking booking);
}