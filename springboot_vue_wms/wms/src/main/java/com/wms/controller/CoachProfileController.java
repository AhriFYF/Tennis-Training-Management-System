package com.wms.controller;

import com.wms.common.Loggable;
import com.wms.entity.coach_users;
import com.wms.service.CoachService;
import com.wms.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 教练信息管理控制器
 */
@RestController
@RequestMapping("/api/coach")
@CrossOrigin // 允许跨域访问，根据项目配置调整
public class CoachProfileController {

    @Autowired
    private CoachService coachService;

    /**
     * 根据用户ID获取教练信息
     * @param userId 用户ID（从sessionStorage中获取的CurUser的id）
     * @return 教练信息
     */
    @Loggable(actionType = "获取 | 教练信息", actionDetail = "根据用户ID获取教练信息")
    @GetMapping("/profile/user/{userId}")
    public ResultVO getCoachByUserId(@PathVariable Integer userId) {
        try {
            coach_users coach = coachService.getCoachByUserId(userId);
            if (coach != null) {
                return ResultVO.success("获取教练信息成功", coach);
            } else {
                return ResultVO.error(404, "未找到对应的教练信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(500, "服务器内部错误: " + e.getMessage());
        }
    }

    /**
     * 根据教练ID获取教练信息
     * @param coachId 教练ID
     * @return 教练信息
     */
    @Loggable(actionType = "获取 | 教练信息", actionDetail = "根据教练ID获取教练信息")
    @GetMapping("/profile/{coachId}")
    public ResultVO getCoachById(@PathVariable Integer coachId) {
        try {
            coach_users coach = coachService.getCoachById(coachId);
            if (coach != null) {
                return ResultVO.success("获取教练信息成功", coach);
            } else {
                return ResultVO.error(404, "未找到教练信息");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(500, "服务器内部错误: " + e.getMessage());
        }
    }

    /**
     * 更新教练信息
     * @param coach 教练信息
     * @return 更新结果
     */
    @Loggable(actionType = "更新 | 教练信息", actionDetail = "更新教练个人信息")
    @PutMapping("/profile")
    public ResultVO updateCoach(@RequestBody coach_users coach) {
        try {
            boolean success = coachService.updateCoach(coach);
            if (success) {
                return ResultVO.success("教练信息更新成功");
            } else {
                return ResultVO.error(400, "教练信息更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(500, "服务器内部错误: " + e.getMessage());
        }
    }

    /**
     * 创建教练信息（如果教练信息不存在时使用）
     * @param coach 教练信息
     * @return 创建结果
     */
    @Loggable(actionType = "新增 | 教练信息", actionDetail = "创建新的教练信息")
    @PostMapping("/profile")
    public ResultVO createCoach(@RequestBody coach_users coach) {
        try {
            boolean success = coachService.createCoach(coach);
            if (success) {
                return ResultVO.success("教练信息创建成功");
            } else {
                return ResultVO.error(400, "教练信息创建失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(500, "服务器内部错误: " + e.getMessage());
        }
    }

    /**
     * 获取教练统计信息
     * @param userId 用户ID
     * @return 统计信息
     */
    @Loggable(actionType = "获取 | 教练统计", actionDetail = "获取教练的统计数据")
    @GetMapping("/stats/{userId}")
    public ResultVO getCoachStats(@PathVariable Integer userId) {
        try {
            // 这里可以根据需要返回教练的统计数据
            // 例如：学员数量、课程数量、评分等
            // 暂时返回模拟数据
            java.util.Map<String, Object> stats = new java.util.HashMap<>();
            stats.put("totalStudents", 15);
            stats.put("totalCourses", 28);
            stats.put("avgRating", 4.8);
            stats.put("totalHours", 156);

            return ResultVO.success("获取统计信息成功", stats);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error(500, "服务器内部错误: " + e.getMessage());
        }
    }
}