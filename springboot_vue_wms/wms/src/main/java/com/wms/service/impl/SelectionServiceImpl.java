package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.student_course_selection;
import com.wms.mapper.SelectionMapper;
import com.wms.service.SelectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SelectionServiceImpl extends ServiceImpl<SelectionMapper, student_course_selection> implements SelectionService {
    @Resource
    private SelectionMapper selectionMapper;

    @Override
    public IPage pageC(IPage<student_course_selection> page) {
        return selectionMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<student_course_selection> page, Wrapper wrapper) {
        return selectionMapper.pageCC(page,wrapper);
    }
}
