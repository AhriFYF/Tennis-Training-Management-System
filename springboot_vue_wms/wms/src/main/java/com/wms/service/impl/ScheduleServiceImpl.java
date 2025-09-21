package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.coach_scheduling;
import com.wms.mapper.ScheduleMapper;
import com.wms.service.ScheduleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, coach_scheduling> implements ScheduleService {
    @Resource
    private ScheduleMapper ScheduleMapper;

    @Override
    public IPage pageC(IPage<coach_scheduling> page) {
        return ScheduleMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<coach_scheduling> page, Wrapper wrapper) {
        return ScheduleMapper.pageCC(page,wrapper);
    }
}