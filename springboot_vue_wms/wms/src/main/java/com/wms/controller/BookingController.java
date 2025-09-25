package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wms.entity.*;
import com.wms.service.*;
import com.wms.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CoachSelectionService coachSelectionService;

    @Autowired
    private CourseInformationService courseInformationService;

    @Autowired
    private StudentCourseSelectionService studentCourseSelectionService;

    @Autowired
    private CoachUsersService coachUsersService;

    // 获取学生已选择的教练
    @GetMapping("/coach/selected")
    public Result getSelectedCoaches(@RequestParam Integer studentId) {
        try {
            QueryWrapper<coach_selection> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_id", studentId);
            queryWrapper.eq("status", "1"); // 只获取已同意的教练

            List<coach_selection> selections = coachSelectionService.list(queryWrapper);
            List<Integer> coachIds = selections.stream()
                    .map(coach_selection::getCoachId)
                    .toList();

            if (coachIds.isEmpty()) {
                return Result.success(null);
            }

            QueryWrapper<coach_users> coachQuery = new QueryWrapper<>();
            coachQuery.in("coach_id", coachIds);
            List<coach_users> coaches = coachUsersService.list(coachQuery);

            return Result.success(coaches);
        } catch (Exception e) {
            return Result.error("获取教练列表失败: " + e.getMessage());
        }
    }

    // 获取教练的课程信息
    @GetMapping("/course/coach/{coachId}")
    public Result getCoachCourses(@PathVariable Integer coachId) {
        try {
            coach_users coach = coachUsersService.getById(coachId);
            if (coach == null) {
                return Result.error("教练不存在");
            }

            QueryWrapper<course_information> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("coach_user", coach.getUserId());
            queryWrapper.eq("status", "0"); // 只获取可预约的课程

            List<course_information> courses = courseInformationService.list(queryWrapper);
            return Result.success(courses);
        } catch (Exception e) {
            return Result.error("获取课程信息失败: " + e.getMessage());
        }
    }

    // 创建预约
    @PostMapping("/create")
    public Result createBooking(@RequestBody Booking booking) {
        try {
            // 1. 保存到booking表
            booking.setCreatedTime(LocalDateTime.now());
            booking.setUpdatedTime(LocalDateTime.now());
            bookingService.save(booking);

            // 2. 保存到student_course_selection表
            student_course_selection selection = new student_course_selection();
            selection.setCourseName(booking.getCourseName());
            selection.setCourseNumber(booking.getCourseNumber());
            selection.setCoachUser(booking.getCoachId());

            coach_users coach = coachUsersService.getById(booking.getCoachId());
            if (coach != null) {
                selection.setCoachName(coach.getName());
            }

            selection.setStudentUsers(booking.getStudentId());
            selection.setCampusId(booking.getCampusId());
            selection.setStatus("0"); // 未确认
            selection.setCreateTime(LocalDateTime.now());
            selection.setUpdateTime(LocalDateTime.now());

            studentCourseSelectionService.save(selection);

            // 3. 更新course_information表的状态
            QueryWrapper<course_information> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_number", booking.getCourseNumber());
            queryWrapper.eq("coach_user", booking.getCoachId());

            course_information course = courseInformationService.getOne(queryWrapper);
            if (course != null) {
                course.setStatus("1"); // 设置为已预约
                courseInformationService.updateById(course);
            }

            return Result.success(booking);
        } catch (Exception e) {
            return Result.error("创建预约失败: " + e.getMessage());
        }
    }

    // 获取学生的预约记录
    @GetMapping("/student/{studentId}")
    public Result getStudentBookings(@PathVariable Integer studentId) {
        try {
            QueryWrapper<Booking> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("student_id", studentId);
            queryWrapper.orderByDesc("created_time");

            List<Booking> bookings = bookingService.list(queryWrapper);
            return Result.success(bookings);
        } catch (Exception e) {
            return Result.error("获取预约记录失败: " + e.getMessage());
        }
    }

    // 取消预约
    @PostMapping("/cancel/{bookingId}")
    public Result cancelBooking(@PathVariable Long bookingId) {
        try {
            Booking booking = bookingService.getById(bookingId);
            if (booking == null) {
                return Result.error("预约记录不存在");
            }

            if (!booking.isPending()) {
                return Result.error("只有待确认的预约可以取消");
            }

            // 更新预约状态
            booking.setStatus(2); // 已取消
            booking.setUpdatedTime(LocalDateTime.now());
            bookingService.updateById(booking);

            // 更新student_course_selection表的状态
            QueryWrapper<student_course_selection> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_number", booking.getCourseNumber());
            queryWrapper.eq("student_users", booking.getStudentId());

            student_course_selection selection = studentCourseSelectionService.getOne(queryWrapper);
            if (selection != null) {
                selection.setStatus("2"); // 已取消
                selection.setUpdateTime(LocalDateTime.now());
                studentCourseSelectionService.updateById(selection);
            }

            // 更新course_information表的状态
            QueryWrapper<course_information> courseQuery = new QueryWrapper<>();
            courseQuery.eq("course_number", booking.getCourseNumber());
            courseQuery.eq("coach_user", booking.getCoachId());

            course_information course = courseInformationService.getOne(courseQuery);
            if (course != null) {
                course.setStatus("0"); // 恢复为可预约
                courseInformationService.updateById(course);
            }

            return Result.success("取消预约成功");
        } catch (Exception e) {
            return Result.error("取消预约失败: " + e.getMessage());
        }
    }

    // 教练确认预约
    @PostMapping("/confirm/{bookingId}")
    public Result confirmBooking(@PathVariable Long bookingId) {
        try {
            Booking booking = bookingService.getById(bookingId);
            if (booking == null) {
                return Result.error("预约记录不存在");
            }

            booking.setStatus(1); // 已确认
            booking.setUpdatedTime(LocalDateTime.now());
            bookingService.updateById(booking);

            // 更新student_course_selection表的状态
            QueryWrapper<student_course_selection> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_number", booking.getCourseNumber());
            queryWrapper.eq("student_users", booking.getStudentId());

            student_course_selection selection = studentCourseSelectionService.getOne(queryWrapper);
            if (selection != null) {
                selection.setStatus("1"); // 已确认
                selection.setUpdateTime(LocalDateTime.now());
                studentCourseSelectionService.updateById(selection);
            }

            return Result.success("预约确认成功");
        } catch (Exception e) {
            return Result.error("确认预约失败: " + e.getMessage());
        }
    }
}