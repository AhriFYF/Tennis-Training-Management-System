package com.wms.controller;

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
    @PutMapping("/process/{selectionId}")
    public Result processSelection(@PathVariable Integer selectionId,
                                   @RequestParam String status) {
        try {
            // 转换前端状态值到数据库状态值
            String dbStatus = "accepted".equals(status) ? "1" : "2";

            boolean success = coachSelectionService.processSelection(selectionId, dbStatus);
            if (success) {
                String action = "accepted".equals(status) ? "同意" : "拒绝";
                return Result.success(action + "成功");
            } else {
                return Result.error("处理失败");
            }
        } catch (Exception e) {
            return Result.error("处理请求失败: " + e.getMessage());
        }
    }

    /**
     * 移除双选关系
     */
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
    @PostMapping("/create")
    public Result createSelection(@RequestParam Integer studentId,
                                  @RequestParam Integer coachId) {
        try {
            boolean success = coachSelectionService.createSelection(studentId, coachId);
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