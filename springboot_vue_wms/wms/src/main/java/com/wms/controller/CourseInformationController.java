package com.wms.controller;

import com.wms.common.Result;
import com.wms.entity.course_information;
import com.wms.service.CourseInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coach/course")
@CrossOrigin(origins = "*")
public class CourseInformationController {
    
    @Autowired
    private CourseInformationService courseInformationService;
    
    /**
     * 获取教练上传的所有课程时间
     * @param coachId 教练ID
     * @return 课程时间列表
     */
    @GetMapping("/list/{coachId}")
    public Result getCoursesByCoachId(@PathVariable Integer coachId) {
        try {
            List<course_information> courses = courseInformationService.getCoursesByCoachId(coachId);
            return Result.suc(courses);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 获取教练的待确认课程预约请求
     * @param coachId 教练ID
     * @return 待确认的课程预约请求列表
     */
    @GetMapping("/pending-requests/{coachId}")
    public Result getPendingCourseRequests(@PathVariable Integer coachId) {
        try {
            List<Map<String, Object>> requests = courseInformationService.getPendingCourseRequestsByCoachId(coachId);
            return Result.suc(requests);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 获取教练的已确认课程
     * @param coachId 教练ID
     * @return 已确认的课程列表
     */
    @GetMapping("/confirmed/{coachId}")
    public Result getConfirmedCourses(@PathVariable Integer coachId) {
        try {
            List<Map<String, Object>> courses = courseInformationService.getConfirmedCoursesByCoachId(coachId);
            return Result.suc(courses);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 上传新的课程时间
     * @param courseInfo 课程信息
     * @return 上传结果
     */
    @PostMapping("/upload")
    public Result uploadCourseTime(@RequestBody course_information courseInfo) {
        try {
            // 检查时间冲突
            if (courseInformationService.checkTimeConflict(courseInfo.getCoachUser(), 
                    courseInfo.getCourseStartTime(), courseInfo.getCourseEndTime())) {
                return Result.fail();
            }
            
            boolean result = courseInformationService.uploadCourseTime(courseInfo);
            if (result) {
                return Result.suc();
            } else {
                return Result.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 处理课程预约请求（确认或拒绝）
     * @param courseId 课程ID
     * @param status 新状态（2-已确认，0-拒绝）
     * @return 处理结果
     */
    @PutMapping("/process/{courseId}")
    public Result processCourseRequest(@PathVariable Integer courseId, @RequestParam String status) {
        try {
            if (!"2".equals(status) && !"0".equals(status)) {
                return Result.fail();
            }
            
            boolean result = courseInformationService.processCourseRequest(courseId, status);
            if (result) {
                return Result.suc();
            } else {
                return Result.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 删除课程时间
     * @param courseId 课程ID
     * @return 删除结果
     */
    @DeleteMapping("/{courseId}")
    public Result deleteCourseTime(@PathVariable Integer courseId) {
        try {
            boolean result = courseInformationService.deleteCourseTime(courseId);
            if (result) {
                return Result.suc();
            } else {
                return Result.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 检查时间冲突
     * @param coachId 教练ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 是否有冲突
     */
    @GetMapping("/check-conflict")
    public Result checkTimeConflict(@RequestParam Integer coachId, 
                                           @RequestParam String startTime, 
                                           @RequestParam String endTime) {
        try {
            LocalDateTime start = LocalDateTime.parse(startTime);
            LocalDateTime end = LocalDateTime.parse(endTime);
            boolean hasConflict = courseInformationService.checkTimeConflict(coachId, start, end);
            return Result.suc(hasConflict);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
}
