package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.match_registration;
import com.wms.mapper.MatchRegistrationMapper;
import com.wms.service.MatchRegistrationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MatchRegistrationServiceImpl extends ServiceImpl<MatchRegistrationMapper, match_registration> implements MatchRegistrationService {
    @Resource
    private MatchRegistrationMapper MatchRegistrationMapper;

    @Override
    public IPage pageC(IPage<match_registration> page) {
        return MatchRegistrationMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<match_registration> page, Wrapper wrapper) {
        return MatchRegistrationMapper.pageCC(page,wrapper);
    }
}
