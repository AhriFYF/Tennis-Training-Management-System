package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.coach_cancels_class;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CoachCancelMapper extends BaseMapper<coach_cancels_class>{

    IPage pageC(IPage<coach_cancels_class> page);

    IPage pageCC(IPage<coach_cancels_class> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
