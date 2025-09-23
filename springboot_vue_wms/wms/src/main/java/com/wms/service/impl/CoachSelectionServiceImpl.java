package com.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.coach_selection;
import com.wms.mapper.CoachSelectionMapper;
import com.wms.service.CoachSelectionService;
import com.wms.dto.CoachSelectionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CoachSelectionServiceImpl extends ServiceImpl<CoachSelectionMapper, coach_selection> implements CoachSelectionService {

    @Autowired
    private CoachSelectionMapper coachSelectionMapper;

    @Override
    public List<CoachSelectionDTO> getPendingSelections(Integer coachId) {
        return coachSelectionMapper.selectPendingSelections(coachId);
    }

    @Override
    public List<CoachSelectionDTO> getAcceptedSelections(Integer coachId) {
        return coachSelectionMapper.selectAcceptedSelections(coachId);
    }

    @Override
    @Transactional
    public boolean processSelection(Integer selectionId, String status) {
        try {
            // 验证状态值
            if (!"1".equals(status) && !"2".equals(status)) {
                throw new IllegalArgumentException("状态值无效");
            }

            int result = coachSelectionMapper.updateStatus(selectionId, status);
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("处理双选请求失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean removeSelection(Integer selectionId) {
        try {
            int result = coachSelectionMapper.deleteById(selectionId);
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("移除双选关系失败: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean createSelection(Integer studentId, Integer coachId) {
        try {
            // 检查是否已存在关系
            if (coachSelectionMapper.existsSelection(studentId, coachId) > 0) {
                throw new RuntimeException("已存在双选关系");
            }

            coach_selection selection = new coach_selection();
            selection.setStudentId(studentId);
            selection.setCoachId(coachId);
            selection.setStatus("0"); // 初始状态为待确认

            int result = coachSelectionMapper.insert(selection);
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("创建双选请求失败: " + e.getMessage());
        }
    }
}