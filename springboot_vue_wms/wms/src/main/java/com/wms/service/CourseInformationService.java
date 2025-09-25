package com.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.course_information;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface CourseInformationService extends IService<course_information> {

    /**
     * 通过教练ID获取教练的用户ID
     */
    Integer getCoachUserIdByCoachId(Integer coachId);

    /**
     * 通过教练用户ID获取可用课程列表
     */
    List<course_information> getAvailableCoursesByCoachUserId(Integer coachUserId);

    /**
     * 获取所有课程列表
     */
    List<course_information> list();

    /**
     * 根据条件查询单个课程
     */
    course_information getOne(QueryWrapper<course_information> queryWrapper);

    /**
     * 分页查询课程
     */
    IPage<course_information> page(IPage<course_information> page, QueryWrapper<course_information> queryWrapper);

    /**
     * 保存课程
     */
    boolean save(course_information course);

    /**
     * 更新课程
     */
    boolean updateById(course_information course);
}