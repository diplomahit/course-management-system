package com.course.controller;

import com.course.dto.ApiResponse;
import com.course.entity.TeacherCourse;
import com.course.service.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teachercourse")
@CrossOrigin
public class TeacherCourseController {
    @Autowired
    private TeacherCourseService teacherCourseService;
    
    @GetMapping("/list")
    public ApiResponse<List<TeacherCourse>> list(@RequestParam(required = false) String keyword,
                                                  @RequestParam(required = false) Long teacherId) {
        List<TeacherCourse> teacherCourses;
        if (teacherId != null) {
            teacherCourses = teacherCourseService.findByTeacherId(teacherId);
        } else if (keyword != null && !keyword.isEmpty()) {
            teacherCourses = teacherCourseService.search(keyword);
        } else {
            teacherCourses = teacherCourseService.findAll();
        }
        return ApiResponse.success(teacherCourses);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<TeacherCourse> getById(@PathVariable Long id) {
        return teacherCourseService.findById(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.error(404, "TeacherCourse not found"));
    }
    
    @PostMapping("/save")
    public ApiResponse<TeacherCourse> save(@RequestBody TeacherCourse teacherCourse) {
        return ApiResponse.success(teacherCourseService.save(teacherCourse));
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        teacherCourseService.deleteById(id);
        return ApiResponse.success(null);
    }
}
