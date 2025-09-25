package com.wms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.coach_selection;
import com.wms.dto.CoachSelectionDTO;
import com.wms.dto.CoachApplicationDTO;

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

    /**
     * 创建双选请求（使用教练userId）
     */
    boolean createSelectionWithUserId(Integer studentId, Integer coachUserId);

    /**
     * 获取我的申请记录
     */
    List<coach_selection> getMyApplications(Integer studentId);

    /**
     * 获取我的申请记录（带教练详细信息）
     */
    List<CoachApplicationDTO> getMyApplicationsWithDetails(Integer studentId);

    /**
     * 计算申请数量
     */
    int countApplications(Integer studentId);

    /**
     * 检查是否已存在双选关系
     */
    boolean existsSelection(Integer studentId, Integer coachId);

    /**
     * 检查是否已存在双选关系（使用教练userId）
     */
    boolean existsSelectionByUserId(Integer studentId, Integer coachUserId);

    /**
     * 解除教练关系
     */
    boolean releaseCoach(Integer studentId, Integer coachId);
}