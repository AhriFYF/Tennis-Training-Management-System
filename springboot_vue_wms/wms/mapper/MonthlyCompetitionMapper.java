package com.wms.mapper;

import com.wms.entity.MonthlyCompetition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MonthlyCompetitionMapper {
    MonthlyCompetition selectById(Long id);
    List<MonthlyCompetition> selectByCampusId(Long campusId);
    List<MonthlyCompetition> selectByDateRange(@Param("start") LocalDate start,
                                               @Param("end") LocalDate end);
    int insert(MonthlyCompetition competition);
    int updateStatus(@Param("id") Long id, @Param("status") String status);
    int delete(Long id);
}
