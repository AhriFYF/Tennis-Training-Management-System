package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wms.entity.course_information;
import com.wms.entity.coach_users;
import com.wms.service.CourseInformationService;
import com.wms.service.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coach/course")
public class CoachCourseController {

    @Autowired
    private CourseInformationService courseInformationService;

    @Autowired
    private CoachService coachUsersService;

    // 获取教练的课程列表
    @GetMapping("/list/{coachId}")
    public ResponseEntity<Map<String, Object>> getCourseList(@PathVariable Integer coachId) {
        Map<String, Object> result = new HashMap<>();
        try {
            QueryWrapper<course_information> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("coach_user", coachId)
                    .orderByDesc("course_start_time");
            List<course_information> courseList = courseInformationService.list(queryWrapper);

            result.put("code", 200);
            result.put("message", "获取成功");
            result.put("data", courseList);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "获取课程列表失败: " + e.getMessage());
            return ResponseEntity.status(500).body(result);
        }
    }

    // 上传课程时间
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadCourse(@RequestBody course_information courseInfo) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 添加详细日志
            System.out.println("接收到的课程信息: " + courseInfo);
            System.out.println("课程名称: " + courseInfo.getCourseName());
            System.out.println("课程类型: " + courseInfo.getCourseTypes());
            System.out.println("开始时间: " + courseInfo.getCourseStartTime());
            System.out.println("结束时间: " + courseInfo.getCourseEndTime());
            System.out.println("教练ID: " + courseInfo.getCoachUser());

            // 验证教练是否存在
            coach_users coach = coachUsersService.getCoachByUserId(courseInfo.getCoachUser());
            if (coach == null) {
                result.put("code", 400);
                result.put("message", "教练不存在，教练ID: " + courseInfo.getCoachUser());
                System.out.println("教练不存在: " + courseInfo.getCoachUser());
                return ResponseEntity.badRequest().body(result);
            }

            // 验证必需字段
            if (courseInfo.getCourseName() == null || courseInfo.getCourseName().trim().isEmpty()) {
                result.put("code", 400);
                result.put("message", "课程名称不能为空");
                return ResponseEntity.badRequest().body(result);
            }

            if (courseInfo.getCourseStartTime() == null) {
                result.put("code", 400);
                result.put("message", "开始时间不能为空");
                return ResponseEntity.badRequest().body(result);
            }

            if (courseInfo.getCourseEndTime() == null) {
                result.put("code", 400);
                result.put("message", "结束时间不能为空");
                return ResponseEntity.badRequest().body(result);
            }

            // 设置默认值
            courseInfo.setStatus("0"); // 0-无人预约
            courseInfo.setHits(0);
            courseInfo.setCollectLen(0);
            courseInfo.setCommentLen(0);
            courseInfo.setCampusId(coach.getCampusId()); // 设置教练所在校区

            // 验证时间合理性
            if (courseInfo.getCourseStartTime().isAfter(courseInfo.getCourseEndTime())) {
                result.put("code", 400);
                result.put("message", "开始时间不能晚于结束时间");
                return ResponseEntity.badRequest().body(result);
            }

            // 检查时间冲突
            QueryWrapper<course_information> conflictQuery = new QueryWrapper<>();
            conflictQuery.eq("coach_user", courseInfo.getCoachUser())
                    .and(wrapper -> wrapper
                            .between("course_start_time", courseInfo.getCourseStartTime(), courseInfo.getCourseEndTime())
                            .or()
                            .between("course_end_time", courseInfo.getCourseStartTime(), courseInfo.getCourseEndTime())
                            .or(w -> w.le("course_start_time", courseInfo.getCourseStartTime())
                                    .ge("course_end_time", courseInfo.getCourseEndTime())));

            int conflictCount = courseInformationService.count(conflictQuery);
            if (conflictCount > 0) {
                result.put("code", 400);
                result.put("message", "该时间段已有其他课程安排，请选择其他时间");
                return ResponseEntity.badRequest().body(result);
            }

            boolean saveResult = courseInformationService.save(courseInfo);
            if (saveResult) {
                result.put("code", 200);
                result.put("message", "课程时间上传成功");
                result.put("data", courseInfo);
                return ResponseEntity.ok(result);
            } else {
                result.put("code", 500);
                result.put("message", "保存到数据库失败");
                return ResponseEntity.status(500).body(result);
            }
        } catch (Exception e) {
            System.err.println("上传课程时发生异常: " + e.getMessage());
            e.printStackTrace();
            result.put("code", 500);
            result.put("message", "上传失败: " + e.getMessage());
            return ResponseEntity.status(500).body(result);
        }
    }

    // 删除课程
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Map<String, Object>> deleteCourse(@PathVariable Integer courseId) {
        Map<String, Object> result = new HashMap<>();
        try {
            course_information course = courseInformationService.getById(courseId);
            if (course == null) {
                result.put("code", 404);
                result.put("message", "课程不存在");
                return ResponseEntity.status(404).body(result);
            }

            // 只有无人预约的课程可以删除
            if (!"0".equals(course.getStatus())) {
                result.put("code", 400);
                result.put("message", "只有无人预约的课程可以删除");
                return ResponseEntity.badRequest().body(result);
            }

            boolean deleteResult = courseInformationService.removeById(courseId);
            if (deleteResult) {
                result.put("code", 200);
                result.put("message", "删除成功");
                return ResponseEntity.ok(result);
            } else {
                result.put("code", 500);
                result.put("message", "删除失败");
                return ResponseEntity.status(500).body(result);
            }
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "删除失败: " + e.getMessage());
            return ResponseEntity.status(500).body(result);
        }
    }

    // 获取课程详情
    @GetMapping("/detail/{courseId}")
    public ResponseEntity<Map<String, Object>> getCourseDetail(@PathVariable Integer courseId) {
        Map<String, Object> result = new HashMap<>();
        try {
            course_information course = courseInformationService.getById(courseId);
            if (course == null) {
                result.put("code", 404);
                result.put("message", "课程不存在");
                return ResponseEntity.status(404).body(result);
            }

            result.put("code", 200);
            result.put("message", "获取成功");
            result.put("data", course);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("code", 500);
            result.put("message", "获取课程详情失败: " + e.getMessage());
            return ResponseEntity.status(500).body(result);
        }
    }
}