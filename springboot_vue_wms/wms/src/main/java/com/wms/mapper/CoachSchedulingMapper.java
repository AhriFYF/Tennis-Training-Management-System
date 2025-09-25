package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.coach_scheduling;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CoachSchedulingMapper extends BaseMapper<coach_scheduling> {

    @Delete("DELETE FROM coach_scheduling WHERE source_id = #{sourceId} AND source_table = #{sourceTable}")
    int deleteBySourceIdAndType(@Param("sourceId") Integer sourceId, @Param("sourceTable") String sourceTable);
}