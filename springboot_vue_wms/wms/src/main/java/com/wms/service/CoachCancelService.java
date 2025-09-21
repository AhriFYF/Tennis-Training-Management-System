package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.coach_cancels_class;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CoachCancelService extends IService<coach_cancels_class>{

    IPage pageC(IPage<coach_cancels_class> page);

    IPage pageCC(IPage<coach_cancels_class> page, Wrapper wrapper);
}
