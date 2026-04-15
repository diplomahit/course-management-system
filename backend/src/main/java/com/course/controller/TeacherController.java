package com.course.controller;

import com.course.dto.ApiResponse;
import com.course.entity.Teacher;
import com.course.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@CrossOrigin
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    
    @GetMapping("/list")
    public ApiResponse<List<Teacher>> list(@RequestParam(required = false) String keyword) {
        List<Teacher> teachers;
        if (keyword != null && !keyword.isEmpty()) {
            teachers = teacherService.search(keyword);
        } else {
            teachers = teacherService.findAll();
        }
        return ApiResponse.success(teachers);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<Teacher> getById(@PathVariable Long id) {
        return teacherService.findById(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.error(404, "Teacher not found"));
    }
    
    @PostMapping("/save")
    public ApiResponse<Teacher> save(@RequestBody Teacher teacher) {
        if (teacher.getId() == null && teacherService.existsByTeacherNo(teacher.getTeacherNo())) {
            return ApiResponse.error(400, "工号已存在");
        }
        return ApiResponse.success(teacherService.save(teacher));
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        teacherService.deleteById(id);
        return ApiResponse.success(null);
    }
}
