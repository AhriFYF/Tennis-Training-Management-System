package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.coach_selection;
import com.wms.dto.CoachSelectionDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoachSelectionMapper extends BaseMapper<coach_selection> {

    // 查询待确认的双选请求（包含学员信息）
    @Select("SELECT cs.selection_id, cs.student_id, cs.coach_id, cs.status, " +
            "su.name as student_name, su.phone as student_phone, su.age as student_age, " +
            "CASE WHEN su.gender = 'M' THEN 1 ELSE 0 END as student_sex " +
            "FROM coach_selection cs " +
            "LEFT JOIN student_users su ON cs.student_id = su.student_id " +
            "WHERE cs.coach_id = #{coachId} AND cs.status = '0'")
    List<CoachSelectionDTO> selectPendingSelections(@Param("coachId") Integer coachId);

    // 查询已确认的双选关系（包含学员信息）
    @Select("SELECT cs.selection_id, cs.student_id, cs.coach_id, cs.status, " +
            "su.name as student_name, su.phone as student_phone, su.age as student_age, " +
            "CASE WHEN su.gender = 'M' THEN 1 ELSE 0 END as student_sex " +
            "FROM coach_selection cs " +
            "LEFT JOIN student_users su ON cs.student_id = su.student_id " +
            "WHERE cs.coach_id = #{coachId} AND cs.status = '1'")
    List<CoachSelectionDTO> selectAcceptedSelections(@Param("coachId") Integer coachId);

    // 根据ID更新状态
    @Update("UPDATE coach_selection SET status = #{status} WHERE selection_id = #{selectionId}")
    int updateStatus(@Param("selectionId") Integer selectionId, @Param("status") String status);

    // 检查是否已存在双选关系
    @Select("SELECT COUNT(*) FROM coach_selection WHERE student_id = #{studentId} AND coach_id = #{coachId}")
    int existsSelection(@Param("studentId") Integer studentId, @Param("coachId") Integer coachId);

    // 检查是否已存在双选关系（使用教练userId）
    @Select("SELECT COUNT(*) FROM coach_selection WHERE student_id = #{studentId} AND coach_id = #{coachUserId}")
    int existsSelectionByUserId(@Param("studentId") Integer studentId, @Param("coachUserId") Integer coachUserId);
}