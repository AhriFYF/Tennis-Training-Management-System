package com.wms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.course_information;
import com.wms.mapper.CourseInformationMapper;
import com.wms.service.CourseInformationService;
import org.springframework.stereotype.Service;

@Service
public class CourseInformationServiceImpl extends ServiceImpl<CourseInformationMapper, course_information> implements CourseInformationService {
}