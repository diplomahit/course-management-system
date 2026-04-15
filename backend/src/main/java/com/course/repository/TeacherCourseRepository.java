package com.course.repository;

import com.course.entity.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Long> {
    List<TeacherCourse> findByTeacherId(Long teacherId);
    List<TeacherCourse> findByCourseId(Long courseId);
    
    @Query("SELECT tc FROM TeacherCourse tc JOIN Teacher t ON tc.teacherId = t.id JOIN Course c ON tc.courseId = c.id WHERE t.name LIKE %?1% OR c.courseName LIKE %?1%")
    List<TeacherCourse> search(String keyword);
}
