package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.entity.student_course_selection;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SelectionService extends IService<student_course_selection>{

    IPage pageC(IPage<student_course_selection> page);

    IPage pageCC(IPage<student_course_selection> page, Wrapper wrapper);
}
