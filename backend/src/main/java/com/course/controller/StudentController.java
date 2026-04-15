package com.course.controller;

import com.course.dto.ApiResponse;
import com.course.entity.Student;
import com.course.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/student")
@CrossOrigin
public class StudentController {
    @Autowired
    private StudentService studentService;
    
    @GetMapping("/list")
    public ApiResponse<List<Student>> list(@RequestParam(required = false) String keyword) {
        List<Student> students;
        if (keyword != null && !keyword.isEmpty()) {
            students = studentService.search(keyword);
        } else {
            students = studentService.findAll();
        }
        return ApiResponse.success(students);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<Student> getById(@PathVariable Long id) {
        return studentService.findById(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.error(404, "Student not found"));
    }
    
    @PostMapping("/save")
    public ApiResponse<Student> save(@RequestBody Student student) {
        if (student.getId() == null && studentService.existsByStudentNo(student.getStudentNo())) {
            return ApiResponse.error(400, "学号已存在");
        }
        return ApiResponse.success(studentService.save(student));
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        studentService.deleteById(id);
        return ApiResponse.success(null);
    }
}
