package com.wms.mapper;

import com.wms.entity.Coach;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoachMapper {
    Coach selectById(Long id);
    List<Coach> selectByCampusId(Long campusId);
    List<Coach> selectByCondition(@Param("name") String name,
                                  @Param("gender") String gender,
                                  @Param("minAge") Integer minAge,
                                  @Param("maxAge") Integer maxAge,
                                  @Param("campusId") Long campusId);
    int updateApprovalStatus(@Param("id") Long id, @Param("isApproved") Boolean isApproved);

    List<Coach> selectAll();
}
