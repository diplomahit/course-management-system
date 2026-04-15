package com.course.service;

import com.course.entity.TeacherCourse;
import com.course.repository.TeacherCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherCourseService {
    @Autowired
    private TeacherCourseRepository teacherCourseRepository;
    
    public List<TeacherCourse> findAll() {
        return teacherCourseRepository.findAll();
    }
    
    public Optional<TeacherCourse> findById(Long id) {
        return teacherCourseRepository.findById(id);
    }
    
    public TeacherCourse save(TeacherCourse teacherCourse) {
        return teacherCourseRepository.save(teacherCourse);
    }
    
    public void deleteById(Long id) {
        teacherCourseRepository.deleteById(id);
    }
    
    public List<TeacherCourse> findByTeacherId(Long teacherId) {
        return teacherCourseRepository.findByTeacherId(teacherId);
    }
    
    public List<TeacherCourse> search(String keyword) {
        return teacherCourseRepository.search(keyword);
    }
}
