package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.Campus;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CampusService extends IService<Campus>{

    IPage pageC(IPage<Campus> page);

    IPage pageCC(IPage<Campus> page, Wrapper wrapper);
}