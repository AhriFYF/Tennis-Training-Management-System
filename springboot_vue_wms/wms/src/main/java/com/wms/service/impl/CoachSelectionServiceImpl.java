package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.coach_selection;
import com.wms.entity.coach_users;
import com.wms.mapper.CoachSelectionMapper;
import com.wms.service.CoachSelectionService;
import com.wms.service.CoachUsersService;
import com.wms.dto.CoachSelectionDTO;
import com.wms.dto.CoachApplicationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoachSelectionServiceImpl extends ServiceImpl<CoachSelectionMapper, coach_selection> implements CoachSelectionService {

    @Autowired
    private CoachSelectionMapper coachSelectionMapper;

    @Autowired
    private CoachUsersService coachUsersService;

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
            // 验证状态值（只允许设置为"1"，即同意）
            if (!"1".equals(status)) {
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

    @Override
    @Transactional
    public boolean createSelectionWithUserId(Integer studentId, Integer coachUserId) {
        try {
            // 检查是否已存在关系
            if (coachSelectionMapper.existsSelectionByUserId(studentId, coachUserId) > 0) {
                throw new RuntimeException("已存在双选关系");
            }

            coach_selection selection = new coach_selection();
            selection.setStudentId(studentId);
            selection.setCoachId(coachUserId); // 使用教练的userId而不是coachId
            selection.setStatus("0"); // 初始状态为待确认

            int result = coachSelectionMapper.insert(selection);
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("创建双选请求失败: " + e.getMessage());
        }
    }

    @Override
    public List<coach_selection> getMyApplications(Integer studentId) {
        LambdaQueryWrapper<coach_selection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(coach_selection::getStudentId, studentId);
        return this.list(wrapper);
    }

    @Override
    public List<CoachApplicationDTO> getMyApplicationsWithDetails(Integer studentId) {
        // 获取原始申请记录
        List<coach_selection> applications = this.getMyApplications(studentId);
        
        // 转换为带详细信息的DTO
        return applications.stream().map(application -> {
            CoachApplicationDTO dto = new CoachApplicationDTO();
            
            // 根据状态值设置格式化的状态显示
            if ("1".equals(application.getStatus())) {
                dto.setStatus("已同意");
            } else {
                dto.setStatus("未同意");
            }

            // 根据coach_id获取教练信息（现在coach_id实际上是user_id）
            coach_users coach = coachUsersService.findByUserId(application.getCoachId());
            if (coach != null) {
                dto.setCoachName(coach.getName());  // 设置教练姓名

            }

            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++"+application.getCoachId());
            
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public int countApplications(Integer studentId) {
        LambdaQueryWrapper<coach_selection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(coach_selection::getStudentId, studentId);
        return this.count(wrapper);
    }

    @Override
    public boolean existsSelection(Integer studentId, Integer coachId) {
        LambdaQueryWrapper<coach_selection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(coach_selection::getStudentId, studentId);
        wrapper.eq(coach_selection::getCoachId, coachId);
        return this.count(wrapper) > 0;
    }

    @Override
    public boolean existsSelectionByUserId(Integer studentId, Integer coachUserId) {
        LambdaQueryWrapper<coach_selection> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(coach_selection::getStudentId, studentId);
        wrapper.eq(coach_selection::getCoachId, coachUserId); // 使用教练的userId进行查询
        return this.count(wrapper) > 0;
    }

    @Override
    @Transactional
    public boolean releaseCoach(Integer studentId, Integer coachId) {
        try {
            LambdaQueryWrapper<coach_selection> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(coach_selection::getStudentId, studentId);
            wrapper.eq(coach_selection::getCoachId, coachId);
            wrapper.eq(coach_selection::getStatus, "1"); // 只能解除已确认的关系
            
            int result = coachSelectionMapper.delete(wrapper);
            return result > 0;
        } catch (Exception e) {
            throw new RuntimeException("解除教练关系失败: " + e.getMessage());
        }
    }
}