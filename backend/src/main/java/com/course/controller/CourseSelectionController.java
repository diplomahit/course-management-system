package com.course.controller;

import com.course.dto.ApiResponse;
import com.course.entity.CourseSelection;
import com.course.service.CourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/selection")
@CrossOrigin
public class CourseSelectionController {
    @Autowired
    private CourseSelectionService courseSelectionService;
    
    @GetMapping("/list")
    public ApiResponse<List<CourseSelection>> list(@RequestParam(required = false) String keyword,
                                                    @RequestParam(required = false) Long studentId) {
        List<CourseSelection> selections;
        if (studentId != null) {
            selections = courseSelectionService.findByStudentId(studentId);
        } else if (keyword != null && !keyword.isEmpty()) {
            selections = courseSelectionService.search(keyword);
        } else {
            selections = courseSelectionService.findAll();
        }
        return ApiResponse.success(selections);
    }
    
    @GetMapping("/{id}")
    public ApiResponse<CourseSelection> getById(@PathVariable Long id) {
        return courseSelectionService.findById(id)
                .map(ApiResponse::success)
                .orElse(ApiResponse.error(404, "Selection not found"));
    }
    
    @PostMapping("/save")
    public ApiResponse<CourseSelection> save(@RequestBody CourseSelection selection) {
        if (selection.getId() == null && 
            courseSelectionService.existsByStudentIdAndCourseId(selection.getStudentId(), selection.getCourseId())) {
            return ApiResponse.error(400, "该学生已选此课程");
        }
        return ApiResponse.success(courseSelectionService.save(selection));
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        courseSelectionService.deleteById(id);
        return ApiResponse.success(null);
    }
}
