package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.teaching_evaluation;
import com.wms.mapper.EvaluationMapper;
import com.wms.service.EvaluationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EvaluationServiceImpl extends ServiceImpl<EvaluationMapper, teaching_evaluation> implements EvaluationService {
    @Resource
    private EvaluationMapper EvaluationMapper;

    @Override
    public IPage pageC(IPage<teaching_evaluation> page) {
        return EvaluationMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<teaching_evaluation> page, Wrapper wrapper) {
        return EvaluationMapper.pageCC(page,wrapper);
    }
}
