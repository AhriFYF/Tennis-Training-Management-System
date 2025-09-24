package com.wms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.match_registration;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MatchRegistrationMapper  extends BaseMapper<match_registration> {

    IPage pageC(IPage<match_registration> page);

    IPage pageCC(IPage<match_registration> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
