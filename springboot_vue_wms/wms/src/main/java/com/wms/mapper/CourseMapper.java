package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.course_information;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CourseMapper extends BaseMapper<course_information>{

    IPage pageC(IPage<course_information> page);

    IPage pageCC(IPage<course_information> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
