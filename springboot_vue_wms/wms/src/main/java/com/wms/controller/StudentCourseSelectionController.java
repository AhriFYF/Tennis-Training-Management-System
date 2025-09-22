package com.wms.controller;

import com.wms.common.Result;
import com.wms.entity.student_course_selection;
import com.wms.service.StudentCourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coach/student-selection")
@CrossOrigin(origins = "*")
public class StudentCourseSelectionController {
    
    @Autowired
    private StudentCourseSelectionService studentCourseSelectionService;
    
    /**
     * 获取课程的所有选课记录
     * @param courseId 课程ID
     * @return 选课记录列表
     */
    @GetMapping("/course/{courseId}")
    public Result getSelectionsByCourseId(@PathVariable Integer courseId) {
        try {
            List<student_course_selection> selections = studentCourseSelectionService.getSelectionsByCourseId(courseId);
            return Result.suc(selections);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 获取教练的所有学员选课记录
     * @param coachId 教练ID
     * @return 选课记录列表
     */
    @GetMapping("/coach/{coachId}")
    public Result getSelectionsByCoachId(@PathVariable Integer coachId) {
        try {
            List<Map<String, Object>> selections = studentCourseSelectionService.getSelectionsByCoachId(coachId);
            return Result.suc(selections);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 获取学员的所有选课记录
     * @param studentId 学员ID
     * @return 选课记录列表
     */
    @GetMapping("/student/{studentId}")
    public Result getSelectionsByStudentId(@PathVariable Integer studentId) {
        try {
            List<Map<String, Object>> selections = studentCourseSelectionService.getSelectionsByStudentId(studentId);
            return Result.suc(selections);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 更新选课记录状态
     * @param selectionId 选课记录ID
     * @param status 新状态
     * @return 更新结果
     */
    @PutMapping("/{selectionId}")
    public Result updateSelectionStatus(@PathVariable Integer selectionId, @RequestParam String status) {
        try {
            boolean result = studentCourseSelectionService.updateSelectionStatus(selectionId, status);
            if (result) {
                return Result.suc();
            } else {
                return Result.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 获取教练和学员的选课记录
     * @param coachId 教练ID
     * @param studentId 学员ID
     * @return 选课记录列表
     */
    @GetMapping("/coach-student")
    public Result getSelectionsByCoachAndStudent(@RequestParam Integer coachId, @RequestParam Integer studentId) {
        try {
            List<Map<String, Object>> selections = studentCourseSelectionService.getSelectionsByCoachAndStudent(coachId, studentId);
            return Result.suc(selections);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
    
    /**
     * 删除选课记录
     * @param selectionId 选课记录ID
     * @return 删除结果
     */
    @DeleteMapping("/{selectionId}")
    public Result deleteSelection(@PathVariable Integer selectionId) {
        try {
            boolean result = studentCourseSelectionService.deleteSelection(selectionId);
            if (result) {
                return Result.suc();
            } else {
                return Result.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
    }
}
