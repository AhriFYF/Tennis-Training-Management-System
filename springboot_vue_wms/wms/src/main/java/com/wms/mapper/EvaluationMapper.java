package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.teaching_evaluation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EvaluationMapper extends BaseMapper<teaching_evaluation>{

    IPage pageC(IPage<teaching_evaluation> page);

    IPage pageCC(IPage<teaching_evaluation> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}