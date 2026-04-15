package com.course.repository;

import com.course.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByTeacherNo(String teacherNo);
    List<Teacher> findByDepartment(String department);
    boolean existsByTeacherNo(String teacherNo);
    
    @Query("SELECT t FROM Teacher t WHERE t.name LIKE %?1% OR t.teacherNo LIKE %?1%")
    List<Teacher> search(String keyword);
}
