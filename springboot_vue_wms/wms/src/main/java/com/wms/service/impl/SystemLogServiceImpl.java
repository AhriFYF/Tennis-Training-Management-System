package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.SystemLog;
import com.wms.mapper.SystemLogMapper;
import com.wms.service.SystemLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SystemLogServiceImpl extends ServiceImpl<SystemLogMapper, SystemLog> implements SystemLogService {
    @Resource
    private SystemLogMapper SystemLogMapper;

    @Override
    public IPage pageC(IPage<SystemLog> page) {
        return SystemLogMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<SystemLog> page, Wrapper wrapper) {
        return SystemLogMapper.pageCC(page,wrapper);
    }
}