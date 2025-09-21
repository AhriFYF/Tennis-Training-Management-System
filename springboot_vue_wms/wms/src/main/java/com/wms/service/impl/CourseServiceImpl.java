package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.course_information;
import com.wms.mapper.CourseMapper;
import com.wms.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, course_information> implements CourseService {
    @Resource
    private CourseMapper courseMapper;

    @Override
    public IPage pageC(IPage<course_information> page) {
        return courseMapper.pageC(page);
    }

    @Override
    public IPage pageCC(IPage<course_information> page, Wrapper wrapper) {
        return courseMapper.pageCC(page,wrapper);
    }
}
