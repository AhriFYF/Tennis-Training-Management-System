package com.wms.mapper;

import com.wms.entity.CoachStudentRelation;
import com.wms.entity.RelationStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoachStudentRelationMapper {
    CoachStudentRelation selectById(Long id);
    List<CoachStudentRelation> selectByCoachId(Long coachId);
    List<CoachStudentRelation> selectByStudentId(Long studentId);
    int insert(CoachStudentRelation relation);
    int updateStatus(@Param("id") Long id, @Param("status") RelationStatus status);
    int delete(Long id);
}
