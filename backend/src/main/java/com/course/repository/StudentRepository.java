package com.course.repository;

import com.course.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentNo(String studentNo);
    List<Student> findByClassName(String className);
    boolean existsByStudentNo(String studentNo);
    
    @Query("SELECT s FROM Student s WHERE s.name LIKE %?1% OR s.studentNo LIKE %?1%")
    List<Student> search(String keyword);
}
