package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.coach_selection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface CoachSelectionMapper extends BaseMapper<coach_selection> {
    
    /**
     * 根据教练ID获取待确认的双选请求
     * @param coachId 教练ID
     * @return 待确认的双选请求列表
     */
    @Select("SELECT cs.*, u.name as studentName, u.phone as studentPhone, u.age as studentAge " +
            "FROM coach_selection cs " +
            "LEFT JOIN user u ON cs.student_id = u.id " +
            "WHERE cs.coach_id = #{coachId} AND cs.status = 'pending'")
    List<Map<String, Object>> getPendingSelectionsByCoachId(@Param("coachId") Integer coachId);
    
    /**
     * 根据教练ID获取已确认的双选关系
     * @param coachId 教练ID
     * @return 已确认的双选关系列表
     */
    @Select("SELECT cs.*, u.name as studentName, u.phone as studentPhone, u.age as studentAge " +
            "FROM coach_selection cs " +
            "LEFT JOIN user u ON cs.student_id = u.id " +
            "WHERE cs.coach_id = #{coachId} AND cs.status = 'accepted'")
    List<Map<String, Object>> getAcceptedSelectionsByCoachId(@Param("coachId") Integer coachId);
    
    /**
     * 更新双选关系状态
     * @param selectionId 双选关系ID
     * @param status 新状态
     * @return 影响行数
     */
    @Update("UPDATE coach_selection SET status = #{status} WHERE selection_id = #{selectionId}")
    int updateSelectionStatus(@Param("selectionId") Integer selectionId, @Param("status") String status);
    
    /**
     * 根据教练ID和学员ID检查双选关系是否存在
     * @param coachId 教练ID
     * @param studentId 学员ID
     * @return 双选关系记录
     */
    @Select("SELECT * FROM coach_selection WHERE coach_id = #{coachId} AND student_id = #{studentId}")
    coach_selection getSelectionByCoachAndStudent(@Param("coachId") Integer coachId, @Param("studentId") Integer studentId);
}
