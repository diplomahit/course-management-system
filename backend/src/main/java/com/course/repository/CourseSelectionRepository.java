package com.course.repository;

import com.course.entity.CourseSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CourseSelectionRepository extends JpaRepository<CourseSelection, Long> {
    List<CourseSelection> findByStudentId(Long studentId);
    List<CourseSelection> findByCourseId(Long courseId);
    List<CourseSelection> findByTeacherId(Long teacherId);
    
    @Query("SELECT cs FROM CourseSelection cs WHERE cs.studentId = ?1 AND cs.status = ?2")
    List<CourseSelection> findByStudentIdAndStatus(Long studentId, String status);
    
    @Query("SELECT cs FROM CourseSelection cs JOIN Student s ON cs.studentId = s.id JOIN Course c ON cs.courseId = c.id WHERE s.name LIKE %?1% OR c.courseName LIKE %?1%")
    List<CourseSelection> search(String keyword);
    
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
