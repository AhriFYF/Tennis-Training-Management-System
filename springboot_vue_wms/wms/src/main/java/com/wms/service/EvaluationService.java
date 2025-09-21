package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.teaching_evaluation;
import com.baomidou.mybatisplus.extension.service.IService;

public interface EvaluationService extends IService<teaching_evaluation>{

    IPage pageC(IPage<teaching_evaluation> page);

    IPage pageCC(IPage<teaching_evaluation> page, Wrapper wrapper);
}
