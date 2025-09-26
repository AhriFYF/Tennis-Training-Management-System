package com.wms.controller;

import com.wms.common.Loggable;
import com.wms.service.CoachSelectionService;
import com.wms.dto.CoachSelectionDTO;
import com.wms.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coach/selection")
public class CoachSelectionController {

    @Autowired
    private CoachSelectionService coachSelectionService;

    /**
     * 获取待确认的双选请求
     */
    @Loggable(actionType = "获取 | 待确认双选", actionDetail = "获取教练待确认的双选请求列表")
    @GetMapping("/pending/{coachId}")
    public Result getPendingSelections(@PathVariable Integer coachId) {
        try {
            List<CoachSelectionDTO> pendingSelections = coachSelectionService.getPendingSelections(coachId);
            // 使用新增的success方法，确保返回格式为{code:200, msg:"成功", data:...}
            return Result.success(pendingSelections);
        } catch (Exception e) {
            return Result.error("获取待确认请求失败: " + e.getMessage());
        }
    }

    /**
     * 获取已确认的双选关系
     */
    @Loggable(actionType = "获取 | 已确认双选", actionDetail = "获取教练已确认的双选关系列表")
    @GetMapping("/accepted/{coachId}")
    public Result getAcceptedSelections(@PathVariable Integer coachId) {
        try {
            List<CoachSelectionDTO> acceptedSelections = coachSelectionService.getAcceptedSelections(coachId);
            return Result.success(acceptedSelections);
        } catch (Exception e) {
            return Result.error("获取已确认关系失败: " + e.getMessage());
        }
    }

    /**
     * 处理双选请求（同意/拒绝）
     */
    @Loggable(actionType = "处理 | 双选请求", actionDetail = "处理教练的双选请求（同意/拒绝）")
    @PutMapping("/process/{selectionId}")
    public Result processSelection(@PathVariable Integer selectionId,
                                   @RequestParam String status) {
        try {
            if ("accepted".equals(status)) {
                // 同意请求，设置状态为"1"
                boolean success = coachSelectionService.processSelection(selectionId, "1");
                if (success) {
                    return Result.success("同意成功");
                } else {
                    return Result.error("同意失败");
                }
            } else {
                // 拒绝请求，设置状态为"2"
                boolean success = coachSelectionService.processSelection(selectionId, "2");
                if (success) {
                    return Result.success("拒绝成功");
                } else {
                    return Result.error("拒绝失败");
                }
            }
        } catch (Exception e) {
            return Result.error("处理请求失败: " + e.getMessage());
        }
    }

    /**
     * 移除双选关系
     */
    @Loggable(actionType = "删除 | 双选关系", actionDetail = "移除教练与学员的双选关系")
    @DeleteMapping("/{selectionId}")
    public Result removeSelection(@PathVariable Integer selectionId) {
        try {
            boolean success = coachSelectionService.removeSelection(selectionId);
            if (success) {
                return Result.success("移除成功");
            } else {
                return Result.error("移除失败");
            }
        } catch (Exception e) {
            return Result.error("移除失败: " + e.getMessage());
        }
    }

    /**
     * 创建双选请求（学员端使用）
     */
    @Loggable(actionType = "新增 | 双选请求", actionDetail = "创建学员与教练的双选请求")
    @PostMapping("/create")
    public Result createSelection(@RequestParam Integer studentId,
                                  @RequestParam Integer coachId) {
        try {
            boolean success = coachSelectionService.createSelection(studentId, coachId);
            System.out.println(studentId);
            if (success) {
                return Result.success("双选请求发送成功");
            } else {
                return Result.error("双选请求发送失败");
            }
        } catch (Exception e) {
            return Result.error("创建请求失败: " + e.getMessage());
        }
    }
}