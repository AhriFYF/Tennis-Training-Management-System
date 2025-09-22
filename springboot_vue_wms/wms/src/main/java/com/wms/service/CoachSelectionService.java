package com.wms.service;

import com.wms.entity.coach_selection;
import com.wms.mapper.CoachSelectionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CoachSelectionService {
    
    @Autowired
    private CoachSelectionMapper coachSelectionMapper;
    
    /**
     * 获取教练的待确认双选请求
     * @param coachId 教练ID
     * @return 待确认的双选请求列表
     */
    public List<Map<String, Object>> getPendingSelectionsByCoachId(Integer coachId) {
        return coachSelectionMapper.getPendingSelectionsByCoachId(coachId);
    }
    
    /**
     * 获取教练的已确认双选关系
     * @param coachId 教练ID
     * @return 已确认的双选关系列表
     */
    public List<Map<String, Object>> getAcceptedSelectionsByCoachId(Integer coachId) {
        return coachSelectionMapper.getAcceptedSelectionsByCoachId(coachId);
    }
    
    /**
     * 处理双选请求（同意或拒绝）
     * @param selectionId 双选关系ID
     * @param status 新状态（accepted/rejected）
     * @return 是否成功
     */
    public boolean processSelectionRequest(Integer selectionId, String status) {
        try {
            int result = coachSelectionMapper.updateSelectionStatus(selectionId, status);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 检查双选关系是否存在
     * @param coachId 教练ID
     * @param studentId 学员ID
     * @return 双选关系记录
     */
    public coach_selection getSelectionByCoachAndStudent(Integer coachId, Integer studentId) {
        return coachSelectionMapper.getSelectionByCoachAndStudent(coachId, studentId);
    }
    
    /**
     * 删除双选关系
     * @param selectionId 双选关系ID
     * @return 是否成功
     */
    public boolean deleteSelection(Integer selectionId) {
        try {
            int result = coachSelectionMapper.deleteById(selectionId);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
