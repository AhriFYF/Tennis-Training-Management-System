package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.student_course_selection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SelectionMapper extends BaseMapper<student_course_selection>{

    IPage pageC(IPage<student_course_selection> page);

    IPage pageCC(IPage<student_course_selection> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}