package com.wms.controller;

import com.wms.common.Loggable;
import com.wms.common.Result;
import com.wms.entity.student_users;
import com.wms.service.CoachFindStudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coach")
public class CoachFindStudentsController {

    @Autowired
    private CoachFindStudentsService coachStudentsService;

    /**
     * 根据教练ID获取学员列表
     */
    @Loggable(actionType = "获取 | 学员列表", actionDetail = "根据教练ID获取学员列表信息")
    @GetMapping("/students/{coachId}")
    public Result getStudentsByCoachId(@PathVariable Integer coachId) {
        try {
            List<student_users> students = coachStudentsService.getStudentsByCoachId(coachId);
            return Result.suc(students);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取学员列表失败: " + e.getMessage());
        }
    }

    /**
     * 获取学员统计信息
     */
    @Loggable(actionType = "获取 | 学员统计", actionDetail = "获取教练的学员统计信息")
    @GetMapping("/students/{coachId}/stats")
    public Result getStudentStats(@PathVariable Integer coachId) {
        try {
            CoachFindStudentsService.StudentStats stats = coachStudentsService.getStudentStats(coachId);
            return Result.suc(stats);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取统计信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取当前登录教练的学员列表
     */
    @Loggable(actionType = "获取 | 我的学员", actionDetail = "获取当前登录教练的学员列表")
    @GetMapping("/my-students")
    public Result getMyStudents(@SessionAttribute("CurUser") Object curUser) {
        try {
            // 从session中获取当前教练ID
            // 这里需要根据你的session结构进行调整
            Integer coachId = getCoachIdFromSession(curUser);
            if (coachId == null) {
                return Result.fail("未找到教练信息");
            }

            List<student_users> students = coachStudentsService.getStudentsByCoachId(coachId);
            return Result.suc(students);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("获取学员列表失败: " + e.getMessage());
        }
    }

    /**
     * 从session中提取教练ID的方法
     * 需要根据你的session结构进行调整
     */
    private Integer getCoachIdFromSession(Object curUser) {
        try {
            // 假设curUser是一个Map或包含id字段的对象
            // 这里需要根据你的实际session结构来解析
            if (curUser instanceof java.util.Map) {
                java.util.Map<?, ?> userMap = (java.util.Map<?, ?>) curUser;
                Object idObj = userMap.get("id");
                if (idObj instanceof Integer) {
                    return (Integer) idObj;
                } else if (idObj instanceof Long) {
                    return ((Long) idObj).intValue();
                } else if (idObj instanceof String) {
                    return Integer.parseInt((String) idObj);
                }
            }
            // 如果无法解析，返回null或默认值
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}