package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Campus;
import com.wms.mapper.CampusMapper;
import com.wms.service.CampusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CampusServiceImpl extends ServiceImpl<CampusMapper, Campus> implements CampusService {
    @Resource
    private CampusMapper CampusMapper;

    @Override
    public IPage pageC(IPage<Campus> page) {
        return CampusMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<Campus> page, Wrapper wrapper) {
        return CampusMapper.pageCC(page,wrapper);
    }

    @Override
    public Campus getCampusById(Integer id) {
        return this.getById(id);
    }

    @Override
    public String getCampusNameById(Integer id) {
        Campus campus = this.getById(id);
        return campus != null ? campus.getName() : "未知校区";
    }
}