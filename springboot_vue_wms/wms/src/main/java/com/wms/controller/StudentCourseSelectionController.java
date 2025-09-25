package com.wms.controller;

import com.wms.common.Loggable;
import com.wms.common.Result;
import com.wms.entity.student_course_selection;
import com.wms.service.StudentCourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/course/selection")
public class StudentCourseSelectionController {

    @Autowired
    private StudentCourseSelectionService studentCourseSelectionService;

    /**
     * 获取学生的课程预约记录
     */
    @Loggable(actionType = "获取 | 课程预约记录", actionDetail = "获取学生的课程预约记录")
    @GetMapping("/{studentId}")
    public Result getMyCourseSelections(@PathVariable Integer studentId) {
        try {
            List<student_course_selection> selections = studentCourseSelectionService.getByStudentId(studentId);
            return Result.success(selections);
        } catch (Exception e) {
            return Result.error("获取预约记录失败: " + e.getMessage());
        }
    }

    /**
     * 创建课程预约
     */
    @Loggable(actionType = "创建 | 课程预约", actionDetail = "学生创建课程预约")
    @PostMapping("/create")
    public Result createCourseSelection(@RequestBody student_course_selection selection) {
        try {
            // 检查余额是否足够
            boolean hasEnoughBalance = studentCourseSelectionService.checkStudentBalance(selection.getStudentUsers(), selection.getCoursePrices());
            if (!hasEnoughBalance) {
                return Result.error("余额不足，请先充值");
            }

            // 检查是否已预约该课程
            if (studentCourseSelectionService.existsSelection(selection.getStudentUsers(), selection.getSourceId())) {
                return Result.error("您已预约该课程");
            }

            boolean success = studentCourseSelectionService.createSelection(selection);
            if (success) {
                return Result.success("预约成功，等待教练确认");
            } else {
                return Result.error("预约失败");
            }
        } catch (Exception e) {
            return Result.error("预约失败: " + e.getMessage());
        }
    }

    /**
     * 取消课程预约
     */
    @Loggable(actionType = "取消 | 课程预约", actionDetail = "学生取消课程预约")
    @DeleteMapping("/cancel/{selectionId}")
    public Result cancelCourseSelection(@PathVariable Integer selectionId) {
        try {
            boolean success = studentCourseSelectionService.cancelSelection(selectionId);
            if (success) {
                return Result.success("取消预约成功");
            } else {
                return Result.error("取消预约失败");
            }
        } catch (Exception e) {
            return Result.error("取消预约失败: " + e.getMessage());
        }
    }
}