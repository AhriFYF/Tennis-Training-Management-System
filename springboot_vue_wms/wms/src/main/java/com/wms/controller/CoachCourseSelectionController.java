package com.wms.controller;

import com.wms.entity.student_course_selection;
import com.wms.mapper.CoachCourseSelectionMapper;
import com.wms.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coach-course-selection")
public class CoachCourseSelectionController {

    @Autowired
    private CoachCourseSelectionMapper coachCourseSelectionMapper;

    /**
     * 获取教练的待确认预约请求
     */
    @GetMapping("/pending-requests/{coachId}")
    public Result getPendingRequests(@PathVariable Integer coachId) {
        List<student_course_selection> pendingRequests =
                coachCourseSelectionMapper.selectByCoachUserIdAndStatus(coachId, "0");
        return Result.suc(pendingRequests);
    }

    /**
     * 获取教练的已确认预约
     */
    @GetMapping("/confirmed/{coachId}")
    public Result getConfirmedRequests(@PathVariable Integer coachId) {
        List<student_course_selection> confirmedRequests =
                coachCourseSelectionMapper.selectByCoachUserIdAndStatus(coachId, "1");
        return Result.suc(confirmedRequests);
    }

    /**
     * 处理预约请求（同意或拒绝）
     */
    @PutMapping("/process/{id}")
    public Result processRequest(@PathVariable Integer id, @RequestParam String status) {
        int result = coachCourseSelectionMapper.updateStatus(id, status);
        if (result > 0) {
            return Result.suc();
        } else {
            return Result.fail();
        }
    }
}