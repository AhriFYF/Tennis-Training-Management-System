package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.course_information;
import com.wms.entity.coach_users;
import com.wms.mapper.CoachUsersMapper;
import com.wms.mapper.CourseInformationMapper;
import com.wms.service.CourseInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseInformationServiceImpl extends ServiceImpl<CourseInformationMapper, course_information> implements CourseInformationService {

    @Autowired
    private CourseInformationMapper courseInformationMapper;

    @Autowired
    private CoachUsersMapper coachUsersMapper;

    @Override
    public Integer getCoachUserIdByCoachId(Integer coachId) {
        // 通过教练ID获取教练信息
        coach_users coach = coachUsersMapper.selectByCoachId(coachId);
        if (coach != null) {
            return coach.getUserId();
        }
        return null;
    }

    @Override
    public List<course_information> getAvailableCoursesByCoachUserId(Integer coachUserId) {
        // 获取教练的可用课程（状态为0-未预约）
        return courseInformationMapper.selectAvailableByCoachUserId(coachUserId);
    }

    @Override
    public List<course_information> list() {
        return courseInformationMapper.selectList(null);
    }

    @Override
    public course_information getOne(QueryWrapper<course_information> queryWrapper) {
        return courseInformationMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<course_information> page(IPage<course_information> page, QueryWrapper<course_information> queryWrapper) {
        return courseInformationMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean save(course_information course) {
        return courseInformationMapper.insert(course) > 0;
    }

    @Override
    public boolean updateById(course_information course) {
        return courseInformationMapper.updateById(course) > 0;
    }
}