package com.wms.controller;

import com.wms.annotation.Loggable;
import com.wms.entity.StudentCourseSelection;
import com.wms.mapper.StudentCourseSelectionMapper;
import com.wms.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-course-selection")
public class StudentCourseSelectionController {
    
    @Autowired
    private StudentCourseSelectionMapper studentCourseSelectionMapper;
    
    /**
     * 获取教练的待确认预约请求
     */
    @Loggable(actionType = "获取 | 待确认预约", actionDetail = "访问数据库获取教练的待确认预约信息")
    @GetMapping("/pending-requests/{coachUserId}")
    public Result getPendingRequests(@PathVariable Long coachUserId) {
        List<StudentCourseSelection> pendingRequests = 
            studentCourseSelectionMapper.selectByCoachUserIdAndStatus(coachUserId, "0");
        return Result.suc(pendingRequests);
    }
    
    /**
     * 获取教练的已确认预约
     */
    @Loggable(actionType = "获取 | 已确认预约", actionDetail = "访问数据库获取教练的已确认预约信息")
    @GetMapping("/confirmed/{coachUserId}")
    public Result getConfirmedRequests(@PathVariable Long coachUserId) {
        List<StudentCourseSelection> confirmedRequests = 
            studentCourseSelectionMapper.selectByCoachUserIdAndStatus(coachUserId, "1");
        return Result.suc(confirmedRequests);
    }
    
    /**
     * 处理预约请求（同意或拒绝）
     */
    @Loggable(actionType = "更新 | 预约状态", actionDetail = "更新预约请求的状态（同意或拒绝）")
    @PutMapping("/process/{id}")
    public Result processRequest(@PathVariable Long id, @RequestParam String status) {
        int result = studentCourseSelectionMapper.updateStatus(id, status);
        if (result > 0) {
            return Result.suc();
        } else {
            return Result.fail();
        }
    }
}