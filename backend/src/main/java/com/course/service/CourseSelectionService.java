package com.course.service;

import com.course.entity.CourseSelection;
import com.course.repository.CourseSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CourseSelectionService {
    @Autowired
    private CourseSelectionRepository courseSelectionRepository;
    
    public List<CourseSelection> findAll() {
        return courseSelectionRepository.findAll();
    }
    
    public Optional<CourseSelection> findById(Long id) {
        return courseSelectionRepository.findById(id);
    }
    
    public CourseSelection save(CourseSelection selection) {
        return courseSelectionRepository.save(selection);
    }
    
    public void deleteById(Long id) {
        courseSelectionRepository.deleteById(id);
    }
    
    public List<CourseSelection> findByStudentId(Long studentId) {
        return courseSelectionRepository.findByStudentId(studentId);
    }
    
    public List<CourseSelection> search(String keyword) {
        return courseSelectionRepository.search(keyword);
    }
    
    public boolean existsByStudentIdAndCourseId(Long studentId, Long courseId) {
        return courseSelectionRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }
}
