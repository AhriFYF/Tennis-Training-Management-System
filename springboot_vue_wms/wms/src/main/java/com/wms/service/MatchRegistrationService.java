package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.match_registration;
import com.baomidou.mybatisplus.extension.service.IService;

public interface MatchRegistrationService extends IService<match_registration>{

    IPage pageC(IPage<match_registration> page);

    IPage pageCC(IPage<match_registration> page, Wrapper wrapper);
}
