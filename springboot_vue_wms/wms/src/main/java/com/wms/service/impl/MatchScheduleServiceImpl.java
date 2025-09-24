package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.match_schedule;
import com.wms.mapper.MatchScheduleMapper;
import com.wms.service.MatchScheduleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MatchScheduleServiceImpl extends ServiceImpl<MatchScheduleMapper, match_schedule> implements MatchScheduleService {
    @Resource
    private MatchScheduleMapper MatchScheduleMapper;

    @Override
    public IPage pageC(IPage<match_schedule> page) {
        return MatchScheduleMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<match_schedule> page, Wrapper wrapper) {
        return MatchScheduleMapper.pageCC(page,wrapper);
    }
}