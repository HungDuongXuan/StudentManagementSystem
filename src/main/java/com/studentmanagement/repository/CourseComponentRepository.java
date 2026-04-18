package com.studentmanagement.repository;

import com.studentmanagement.entity.CourseComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseComponentRepository extends JpaRepository<CourseComponent, Long> {

    List<CourseComponent> findByCourseId(Long courseId);

    @Query(value = "SELECT COALESCE(SUM(weight), 0) FROM student_management_test.course_components WHERE course_id = :courseId",
            nativeQuery = true)
    double sumWeightByCourseId(@Param("courseId") Long courseId);
}
