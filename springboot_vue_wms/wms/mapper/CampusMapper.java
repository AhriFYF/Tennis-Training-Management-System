package com.wms.mapper;
import com.wms.entity.Campus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CampusMapper {
    Campus selectById(Long id);
    List<Campus> selectAll();
    List<Campus> selectByParentId(Long parentId);
    int insert(Campus campus);
    int update(Campus campus);
    int delete(Long id);
}
