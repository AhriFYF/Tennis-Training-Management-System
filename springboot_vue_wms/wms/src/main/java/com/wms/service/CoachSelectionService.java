package com.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.coach_selection;
import com.wms.dto.CoachSelectionDTO;

import java.util.List;

public interface CoachSelectionService extends IService<coach_selection> {

    /**
     * 获取待确认的双选请求
     */
    List<CoachSelectionDTO> getPendingSelections(Integer coachId);

    /**
     * 获取已确认的双选关系
     */
    List<CoachSelectionDTO> getAcceptedSelections(Integer coachId);

    /**
     * 处理双选请求（同意/拒绝）
     */
    boolean processSelection(Integer selectionId, String status);

    /**
     * 移除双选关系
     */
    boolean removeSelection(Integer selectionId);

    /**
     * 创建双选请求
     */
    boolean createSelection(Integer studentId, Integer coachId);
}