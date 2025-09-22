package com.wms.mapper;

import com.wms.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface StudentMapper {
    Student selectById(Long id);
    List<Student> selectByCampusId(Long campusId);
    int updateAccountBalance(@Param("id") Long id, @Param("amount") BigDecimal amount);
}
