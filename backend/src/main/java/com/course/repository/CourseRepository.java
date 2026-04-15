package com.course.repository;

import com.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCourseCode(String courseCode);
    List<Course> findBySemester(String semester);
    boolean existsByCourseCode(String courseCode);
    
    @Query("SELECT c FROM Course c WHERE c.courseName LIKE %?1% OR c.courseCode LIKE %?1%")
    List<Course> search(String keyword);
}
