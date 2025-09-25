package com.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.student_course_selection;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface StudentCourseSelectionService extends IService<student_course_selection> {

    /**
     * 根据学生ID获取课程预约记录
     */
    List<student_course_selection> getByStudentId(Integer studentId);

    /**
     * 创建课程预约
     */
    boolean createSelection(student_course_selection selection);

    /**
     * 取消课程预约
     */
    boolean cancelSelection(Integer selectionId);

    /**
     * 检查学生是否已预约该课程
     */
    boolean existsSelection(Integer studentId, Integer courseId);

    /**
     * 检查学生余额是否足够
     */
    boolean checkStudentBalance(Integer studentId, Double coursePrice);

    /**
     * 获取所有预约记录
     */
    List<student_course_selection> list();

    /**
     * 根据条件查询单个预约记录
     */
    student_course_selection getOne(QueryWrapper<student_course_selection> queryWrapper);

    /**
     * 分页查询预约记录
     */
    IPage<student_course_selection> page(IPage<student_course_selection> page, QueryWrapper<student_course_selection> queryWrapper);

    /**
     * 保存预约记录
     */
    boolean save(student_course_selection selection);

    /**
     * 更新预约记录
     */
    boolean updateById(student_course_selection selection);
}