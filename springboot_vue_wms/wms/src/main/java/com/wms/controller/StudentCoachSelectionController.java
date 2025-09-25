package com.wms.controller;

import com.wms.common.Loggable;
import com.wms.common.Result;
import com.wms.entity.coach_selection;
import com.wms.entity.coach_users;
import com.wms.service.CoachSelectionService;
import com.wms.service.CoachUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student/coach")
public class StudentCoachSelectionController {

    @Autowired
    private CoachUsersService coachUsersService;

    @Autowired
    private CoachSelectionService coachSelectionService;

    /**
     * 获取所有教练列表
     */
    @Loggable(actionType = "获取 | 所有教练", actionDetail = "获取所有教练列表")
    @GetMapping("/list")
    public Result getAllCoaches() {
        try {
            List<coach_users> coaches = coachUsersService.list();
            return Result.success(coaches);
        } catch (Exception e) {
            return Result.error("获取教练列表失败: " + e.getMessage());
        }
    }

    /**
     * 搜索教练
     */
    @Loggable(actionType = "搜索 | 教练", actionDetail = "根据条件搜索教练")
    @PostMapping("/search")
    public Result searchCoaches(@RequestBody coach_users searchCondition) {
        try {
            List<coach_users> coaches = coachUsersService.searchCoaches(searchCondition);
            return Result.success(coaches);
        } catch (Exception e) {
            return Result.error("搜索教练失败: " + e.getMessage());
        }
    }

    /**
     * 获取我的教练（已确认的）
     */
    @Loggable(actionType = "获取 | 我的教练", actionDetail = "获取学生已确认的教练列表")
    @GetMapping("/my/list/{studentId}")
    public Result getMyCoaches(@PathVariable Integer studentId) {
        try {
            List<coach_users> myCoaches = coachUsersService.getMyCoaches(studentId);
            return Result.success(myCoaches);
        } catch (Exception e) {
            return Result.error("获取我的教练失败: " + e.getMessage());
        }
    }

    /**
     * 获取我的教练申请记录
     */
    @Loggable(actionType = "获取 | 教练申请", actionDetail = "获取学生的教练申请记录")
    @GetMapping("/application/list/{studentId}")
    public Result getMyApplications(@PathVariable Integer studentId) {
        try {
            List<com.wms.dto.CoachApplicationDTO> applications = coachSelectionService.getMyApplicationsWithDetails(studentId);
            return Result.success(applications);
        } catch (Exception e) {
            return Result.error("获取申请记录失败: " + e.getMessage());
        }
    }

    /**
     * 申请教练
     */
    @Loggable(actionType = "申请 | 教练", actionDetail = "学生申请教练")
    @PostMapping("/apply")
    public Result applyCoach(@RequestBody coach_selection application) {
        try {
            // 检查是否已达到申请上限
            int applicationCount = coachSelectionService.countApplications(application.getStudentId());
            if (applicationCount >= 2) {
                return Result.error("每位学员最多选择两位教练");
            }

            // 根据传入的coachId（实际上是coach_users表的coach_id）查找对应的教练信息
            coach_users coach = coachUsersService.findByCoachId(application.getCoachId());
            if (coach == null) {
                return Result.error("未找到指定的教练");
            }

            // 获取教练的user_id
            Integer coachUserId = coach.getUserId();

            // 检查是否已申请该教练（使用教练的userId）
            if (coachSelectionService.existsSelectionByUserId(application.getStudentId(), coachUserId)) {
                return Result.error("您已申请该教练");
            }

            // 创建申请（使用教练的userId）
            boolean success = coachSelectionService.createSelectionWithUserId(application.getStudentId(), coachUserId);
            if (success) {
                return Result.success("申请已提交，等待教练确认");
            } else {
                return Result.error("申请提交失败");
            }
        } catch (Exception e) {
            return Result.error("申请失败: " + e.getMessage());
        }
    }

    /**
     * 撤销教练申请
     */
    @Loggable(actionType = "撤销 | 教练申请", actionDetail = "撤销学生的教练申请")
    @DeleteMapping("/application/cancel/{applicationId}")
    public Result cancelApplication(@PathVariable Integer applicationId) {
        try {
            boolean success = coachSelectionService.removeSelection(applicationId);
            if (success) {
                return Result.success("撤销申请成功");
            } else {
                return Result.error("撤销申请失败");
            }
        } catch (Exception e) {
            return Result.error("撤销申请失败: " + e.getMessage());
        }
    }

    /**
     * 解除教练关系
     */
    @Loggable(actionType = "解除 | 教练关系", actionDetail = "解除学生与教练的关系")
    @DeleteMapping("/release/{studentId}/{coachId}")
    public Result releaseCoach(@PathVariable Integer studentId, @PathVariable Integer coachId) {
        try {
            boolean success = coachSelectionService.releaseCoach(studentId, coachId);
            if (success) {
                return Result.success("解除关系成功");
            } else {
                return Result.error("解除关系失败");
            }
        } catch (Exception e) {
            return Result.error("解除关系失败: " + e.getMessage());
        }
    }
}