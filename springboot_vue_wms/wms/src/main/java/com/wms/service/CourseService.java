package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.course_information;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CourseService extends IService<course_information>{

    IPage pageC(IPage<course_information> page);

    IPage pageCC(IPage<course_information> page, Wrapper wrapper);
}
