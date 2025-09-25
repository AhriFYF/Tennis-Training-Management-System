package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wms.entity.*;
import com.wms.mapper.*;
import com.wms.service.StudentCourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentCourseSelectionServiceImpl extends ServiceImpl<StudentCourseSelectionMapper, student_course_selection> implements StudentCourseSelectionService {

    @Autowired
    private StudentCourseSelectionMapper studentCourseSelectionMapper;

    @Autowired
    private CourseInformationMapper courseInformationMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CoachUsersMapper coachUsersMapper;

    @Autowired
    private CoachSchedulingMapper coachSchedulingMapper;

    @Override
    public List<student_course_selection> getByStudentId(Integer studentId) {
        return studentCourseSelectionMapper.selectByStudentId(studentId);
    }

    @Override
    @Transactional
    public boolean createSelection(student_course_selection selection) {
        try {
            // 1. 获取课程信息
            course_information course = courseInformationMapper.selectById(selection.getSourceId());
            if (course == null) {
                return false;
            }

            // 2. 获取学生信息
            User student = userMapper.selectById(selection.getStudentUsers());
            if (student == null) {
                return false;
            }

            // 3. 获取教练信息
            coach_users coach = coachUsersMapper.findByUserId(course.getCoachUser());
            if (coach == null) {
                return false;
            }

            // 4. 设置预约信息
            selection.setCourseName(course.getCourseName());
           // selection.setCourseNumber(course.getCourseNumber());
            selection.setCourseTypes(course.getCourseTypes());
            selection.setCoachUser(course.getCoachUser());
            selection.setCoachName(course.getCoachName());
            selection.setCourseHours(course.getCourseHours());
            selection.setCoursePrices(course.getCoursePrices());
            selection.setName(student.getName());
            selection.setPhoneNumber(student.getPhone());
            selection.setPayState("0"); // 未支付
            selection.setStatus("0"); // 未确认
            selection.setCreateTime(LocalDateTime.now());
            selection.setUpdateTime(LocalDateTime.now());
            selection.setSourceTable("course_information");
            selection.setCampusId(student.getCampusId());

            // 5. 扣除学生余额
            BigDecimal newBalance = student.getBalance().subtract(BigDecimal.valueOf(course.getCoursePrices()));
            student.setBalance(newBalance);
            userMapper.updateById(student);

            // 6. 更新课程状态为已预约
            course.setStatus("1");
            courseInformationMapper.updateById(course);

            // 7. 创建教练排课记录
            coach_scheduling scheduling = new coach_scheduling();
            scheduling.setCourseName(course.getCourseName());
           // scheduling.setCourseNumber(course.getCourseNumber());
            scheduling.setCoachUser(course.getCoachUser());
            scheduling.setCoachName(course.getCoachName());
            scheduling.setStudentUsers(selection.getStudentUsers());
            scheduling.setName(student.getName());
           // scheduling.setScheduleDate(course.getCourseStartTime());
            scheduling.setSchedulingPeriod(calculateTimePeriod(course.getCourseStartTime(), course.getCourseEndTime()));
            scheduling.setContentOfCourses(course.getCourseIntroduction());
            scheduling.setSourceId(course.getCourseInformationId());
            scheduling.setSourceUserId(selection.getStudentUsers());
            scheduling.setCampusId(student.getCampusId());

            coachSchedulingMapper.insert(scheduling);

            // 8. 保存预约记录
            return studentCourseSelectionMapper.insert(selection) > 0;
        } catch (Exception e) {
            throw new RuntimeException("创建课程预约失败", e);
        }
    }

    @Override
    @Transactional
    public boolean cancelSelection(Integer selectionId) {
        try {
            // 1. 获取预约记录
            student_course_selection selection = studentCourseSelectionMapper.selectById(selectionId);
            if (selection == null) {
                return false;
            }

            // 2. 获取课程信息
            course_information course = courseInformationMapper.selectById(selection.getSourceId());
            if (course == null) {
                return false;
            }


            // 3. 获取学生信息
            User student = userMapper.selectById(selection.getStudentUsers());
            if (student == null) {
                return false;
            }

            // 4. 退还余额
            BigDecimal newBalance = student.getBalance().add(BigDecimal.valueOf(selection.getCoursePrices()));
            student.setBalance(newBalance);
            userMapper.updateById(student);

            // 5. 更新课程状态为可预约
            course.setStatus("0");
            courseInformationMapper.updateById(course);

            // 6. 删除教练排课记录
            coachSchedulingMapper.deleteBySourceIdAndType(selection.getSourceId(), "course_information");

            // 7. 更新预约状态为已取消
            selection.setStatus("2"); // 已取消
            selection.setUpdateTime(LocalDateTime.now());

            return studentCourseSelectionMapper.updateById(selection) > 0;
        } catch (Exception e) {
            throw new RuntimeException("取消课程预约失败", e);
        }
    }

    @Override
    public boolean existsSelection(Integer studentId, Integer courseId) {
        return studentCourseSelectionMapper.countByStudentAndCourse(studentId, courseId) > 0;
    }

    @Override
    public boolean checkStudentBalance(Integer studentId, Double coursePrice) {
        User student = userMapper.selectById(studentId);
        if (student == null) {
            return false;
        }
        return student.getBalance().compareTo(BigDecimal.valueOf(coursePrice)) >= 0;
    }

    @Override
    public List<student_course_selection> list() {
        return studentCourseSelectionMapper.selectList(null);
    }

    @Override
    public student_course_selection getOne(QueryWrapper<student_course_selection> queryWrapper) {
        return studentCourseSelectionMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<student_course_selection> page(IPage<student_course_selection> page, QueryWrapper<student_course_selection> queryWrapper) {
        return studentCourseSelectionMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean save(student_course_selection selection) {
        return studentCourseSelectionMapper.insert(selection) > 0;
    }

    @Override
    public boolean updateById(student_course_selection selection) {
        return studentCourseSelectionMapper.updateById(selection) > 0;
    }

    private String calculateTimePeriod(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.getHour() + ":" + String.format("%02d", startTime.getMinute()) +
                "-" + endTime.getHour() + ":" + String.format("%02d", endTime.getMinute());
    }
}