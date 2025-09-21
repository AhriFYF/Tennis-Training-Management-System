package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.coach_scheduling;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ScheduleService extends IService<coach_scheduling>{

    IPage pageC(IPage<coach_scheduling> page);

    IPage pageCC(IPage<coach_scheduling> page, Wrapper wrapper);
}
