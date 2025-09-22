package com.wms.controller;

import com.wms.common.Result;
import com.wms.entity.coach_selection;
import com.wms.service.CoachSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coach/selection")
@CrossOrigin(origins = "*")
public class CoachSelectionController {
    
    @Autowired
    private CoachSelectionService coachSelectionService;
    
    /**
     * 获取教练的待确认双选请求
     * @param coachId 教练ID
     * @return 待确认的双选请求列表
     */
    @GetMapping("/pending/{coachId}")
    public Result getPendingSelections(@PathVariable Integer coachId) {
        try {
            List<Map<String, Object>> selections = coachSelectionService.getPendingSelectionsByCoachId(coachId);
            return Result.suc(selections);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 获取教练的已确认双选关系
     * @param coachId 教练ID
     * @return 已确认的双选关系列表
     */
    @GetMapping("/accepted/{coachId}")
    public Result getAcceptedSelections(@PathVariable Integer coachId) {
        try {
            List<Map<String, Object>> selections = coachSelectionService.getAcceptedSelectionsByCoachId(coachId);
            return Result.suc(selections);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 处理双选请求（同意或拒绝）
     * @param selectionId 双选关系ID
     * @param status 新状态（accepted/rejected）
     * @return 处理结果
     */
    @PutMapping("/process/{selectionId}")
    public Result processSelectionRequest(@PathVariable Integer selectionId, @RequestParam String status) {
        try {
            if (!"accepted".equals(status) && !"rejected".equals(status)) {
                return Result.fail();
            }
            
            boolean result = coachSelectionService.processSelectionRequest(selectionId, status);
            if (result) {
                return Result.suc();
            } else {
                return Result.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 删除双选关系
     * @param selectionId 双选关系ID
     * @return 删除结果
     */
    @DeleteMapping("/{selectionId}")
    public Result deleteSelection(@PathVariable Integer selectionId) {
        try {
            boolean result = coachSelectionService.deleteSelection(selectionId);
            if (result) {
                return Result.suc();
            } else {
                return Result.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 检查双选关系是否存在
     * @param coachId 教练ID
     * @param studentId 学员ID
     * @return 双选关系记录
     */
    @GetMapping("/check")
    public Result checkSelection(@RequestParam Integer coachId, @RequestParam Integer studentId) {
        try {
            coach_selection selection = coachSelectionService.getSelectionByCoachAndStudent(coachId, studentId);
            if (selection != null) {
                return Result.suc(selection);
            } else {
                return Result.suc(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
}
