package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.SystemLog;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SystemLogService extends IService<SystemLog>{

    IPage pageC(IPage<SystemLog> page);

    IPage pageCC(IPage<SystemLog> page, Wrapper wrapper);
}
