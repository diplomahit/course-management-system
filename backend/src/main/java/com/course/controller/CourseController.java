package com.course.controller;

import com.course.dto.ApiResponse;
import com.course.entity.Course;
import com.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;
    
    @GetMapping("/list")
    public ApiResponse<List<Course>> list(@RequestParam(required = false) String keyword) {
        List<Course> courses;
        if (keyword != null && !keyword.isEmpty()) {
            courses = courseService.search(keyword);
        } else {
            courses = courseService.findAll();
        }
        return ApiResponse.success(courses);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<Course> getById(@PathVariable Long id) {
        return courseService.findById(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.error(404, "Course not found"));
    }
    
    @PostMapping("/save")
    public ApiResponse<Course> save(@RequestBody Course course) {
        if (course.getId() == null && courseService.existsByCourseCode(course.getCourseCode())) {
            return ApiResponse.error(400, "课程代码已存在");
        }
        return ApiResponse.success(courseService.save(course));
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        courseService.deleteById(id);
        return ApiResponse.success(null);
    }
}
