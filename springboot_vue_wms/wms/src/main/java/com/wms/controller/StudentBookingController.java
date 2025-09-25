package com.wms.controller;

import com.wms.common.Loggable;
import com.wms.common.Result;
import com.wms.entity.coach_selection;
import com.wms.entity.course_information;
import com.wms.entity.student_course_selection;
import com.wms.service.CoachSelectionService;
import com.wms.service.CourseService;
import com.wms.service.StudentCourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/booking")
public class StudentBookingController {

    @Autowired
    private CoachSelectionService coachSelectionService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentCourseSelectionService studentCourseSelectionService;

    /**
     * 测试接口
     */
    @Loggable(actionType = "测试", actionDetail = "测试接口是否正常工作")
    @GetMapping("/test")
    public Result test() {
        return Result.success("接口正常工作");
    }

    /**
     * 获取学生已确认的教练列表
     */
    @Loggable(actionType = "获取 | 教练列表", actionDetail = "获取学生已确认的教练列表")
    @GetMapping("/coaches/{studentId}")
    public Result getMyCoaches(@PathVariable Integer studentId) {
        try {
            List<coach_selection> selections = coachSelectionService.getMyApplications(studentId);
            return Result.success(selections);
        } catch (Exception e) {
            return Result.error("获取教练列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据教练userId获取该教练的可用课程
     */
    @Loggable(actionType = "获取 | 教练课程", actionDetail = "根据教练userId获取该教练的可用课程")
    @GetMapping("/courses/{coachUserId}")
    public Result getCoursesByCoach(@PathVariable Integer coachUserId) {
        try {
            // 打印调试信息
            System.out.println("查询教练ID为 " + coachUserId + " 的课程");
            
            List<course_information> courses = courseService.lambdaQuery()
                    .eq(course_information::getCoachUser, coachUserId)
                    .eq(course_information::getStatus, "0") // 只获取未预约的课程
                    .gt(course_information::getCourseStartTime, new java.util.Date()) // 只获取未开始的课程
                    .orderByAsc(course_information::getCourseStartTime)
                    .list();
            
            System.out.println("查询到 " + courses.size() + " 个课程");
            return Result.success(courses);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("获取课程列表失败: " + e.getMessage());
        }
    }

    /**
     * 学生预约课程
     */
    @Loggable(actionType = "预约 | 课程", actionDetail = "学生预约课程")
    @PostMapping("/select")
    public Result selectCourse(@RequestBody student_course_selection selection) {
        try {
            // 检查余额是否足够
            boolean hasEnoughBalance = studentCourseSelectionService.checkStudentBalance(
                selection.getStudentUsers(), selection.getCoursePrices());
            if (!hasEnoughBalance) {
                return Result.error("余额不足，请先充值");
            }

            // 检查是否已预约该课程
            if (studentCourseSelectionService.existsSelection(
                selection.getStudentUsers(), selection.getSourceId())) {
                return Result.error("您已预约该课程");
            }

            // 创建课程预约
            boolean success = studentCourseSelectionService.createSelection(selection);
            if (success) {
                return Result.success("预约申请已提交，等待教练确认");
            } else {
                return Result.error("预约失败");
            }
        } catch (Exception e) {
            return Result.error("预约失败: " + e.getMessage());
        }
    }
}