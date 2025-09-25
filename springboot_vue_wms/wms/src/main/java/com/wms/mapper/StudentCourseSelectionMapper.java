package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.student_course_selection;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentCourseSelectionMapper extends BaseMapper<student_course_selection> {
    // 可以添加自定义的复杂查询方法
}