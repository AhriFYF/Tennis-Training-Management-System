package com.wms.controller;

import com.wms.common.Loggable;
import com.wms.common.Result;
import com.wms.entity.course_information;
import com.wms.service.CourseInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/course")
public class StudentCourseController {

    @Autowired
    private CourseInformationService courseInformationService;

    /**
     * 获取教练的可用课程
     */
    @Loggable(actionType = "获取 | 教练课程", actionDetail = "获取教练的可用课程列表")
    @GetMapping("/coach/{coachId}")
    public Result getCoachCourses(@PathVariable Integer coachId) {
        try {
            // 先通过coachId获取userId
            Integer coachUserId = courseInformationService.getCoachUserIdByCoachId(coachId);
            if (coachUserId == null) {
                return Result.error("未找到教练信息");
            }

            List<course_information> courses = courseInformationService.getAvailableCoursesByCoachUserId(coachUserId);
            return Result.success(courses);
        } catch (Exception e) {
            return Result.error("获取课程列表失败: " + e.getMessage());
        }
    }
}