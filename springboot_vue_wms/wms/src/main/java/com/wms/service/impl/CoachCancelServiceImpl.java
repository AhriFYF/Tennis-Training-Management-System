package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.coach_cancels_class;
import com.wms.mapper.CoachCancelMapper;
import com.wms.service.CoachCancelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CoachCancelServiceImpl extends ServiceImpl<CoachCancelMapper, coach_cancels_class> implements CoachCancelService {
    @Resource
    private CoachCancelMapper CoachCancelMapper;

    @Override
    public IPage pageC(IPage<coach_cancels_class> page) {
        return CoachCancelMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<coach_cancels_class> page, Wrapper wrapper) {
        return CoachCancelMapper.pageCC(page,wrapper);
    }
}
