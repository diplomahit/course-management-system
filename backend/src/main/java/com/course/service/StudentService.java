package com.course.service;

import com.course.entity.Student;
import com.course.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    
    public List<Student> findAll() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Student save(Student student) {
        return studentRepository.save(student);
    }
    
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }
    
    public List<Student> search(String keyword) {
        return studentRepository.search(keyword);
    }
    
    public boolean existsByStudentNo(String studentNo) {
        return studentRepository.existsByStudentNo(studentNo);
    }
}
