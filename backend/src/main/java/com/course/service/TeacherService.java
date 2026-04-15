package com.course.service;

import com.course.entity.Teacher;
import com.course.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    
    public List<Teacher> findAll() {
        return teacherRepository.findAll();
    }
    
    public Optional<Teacher> findById(Long id) {
        return teacherRepository.findById(id);
    }
    
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(teacher);
    }
    
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }
    
    public List<Teacher> search(String keyword) {
        return teacherRepository.search(keyword);
    }
    
    public boolean existsByTeacherNo(String teacherNo) {
        return teacherRepository.existsByTeacherNo(teacherNo);
    }
}
