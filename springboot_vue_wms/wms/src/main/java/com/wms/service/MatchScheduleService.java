package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.match_schedule;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MatchScheduleService extends IService<match_schedule>{

    IPage pageC(IPage<match_schedule> page);

    IPage pageCC(IPage<match_schedule> page, Wrapper wrapper);
}