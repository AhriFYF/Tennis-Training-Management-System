package com.wms.service;

import com.wms.entity.student_users;
import com.wms.mapper.CoachSelectionMapper;
import com.wms.mapper.C0achFindStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachFindStudentsService {

    @Autowired
    private CoachSelectionMapper coachSelectionMapper;

    @Autowired
    private C0achFindStudentMapper studentUsersMapper;

    /**
     * 根据教练ID获取学员列表
     */
    public List<student_users> getStudentsByCoachId(Integer coachId) {
        // 方法1：直接使用SQL联表查询（推荐）
        return studentUsersMapper.findStudentsByCoachId(coachId);

        // 方法2：先查关系表，再查学生表（备用方案）
        // List<Integer> studentIds = coachSelectionMapper.findStudentIdsByCoachId(coachId);
        // if (studentIds == null || studentIds.isEmpty()) {
        //     return new ArrayList<>();
        // }
        // return studentUsersMapper.findStudentsByIds(studentIds);
    }

    /**
     * 获取学员统计信息
     */
    public StudentStats getStudentStats(Integer coachId) {
        List<student_users> students = getStudentsByCoachId(coachId);

        StudentStats stats = new StudentStats();
        stats.setTotalStudents(students.size());
        stats.setMaleStudents((int) students.stream().filter(s -> "M".equals(s.getGender())).count());
        stats.setFemaleStudents((int) students.stream().filter(s -> "F".equals(s.getGender())).count());

        return stats;
    }

    /**
     * 统计信息DTO
     */
    public static class StudentStats {
        private int totalStudents;
        private int maleStudents;
        private int femaleStudents;

        // getter和setter方法
        public int getTotalStudents() {
            return totalStudents;
        }

        public void setTotalStudents(int totalStudents) {
            this.totalStudents = totalStudents;
        }

        public int getMaleStudents() {
            return maleStudents;
        }

        public void setMaleStudents(int maleStudents) {
            this.maleStudents = maleStudents;
        }

        public int getFemaleStudents() {
            return femaleStudents;
        }

        public void setFemaleStudents(int femaleStudents) {
            this.femaleStudents = femaleStudents;
        }
    }
}