package com.studentmanagement.repository;

import com.studentmanagement.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByFacultyId(Long facultyId);
    boolean existsByName(String name);

    @Query(value = """
        SELECT c.* FROM courses c
        JOIN faculties f ON c.faculty_id = f.id
        WHERE c.status != 'DELETED'
        AND (:name IS NULL OR c.name ILIKE CONCAT('%', :name, '%'))
        AND (:facultyId IS NULL OR c.faculty_id = :facultyId)
        ORDER BY c.created_at DESC
        """,
        countQuery = """
        SELECT COUNT(*) FROM courses c
        WHERE c.status != 'DELETED'
        AND (:name IS NULL OR c.name ILIKE CONCAT('%', :name, '%'))
        AND (:facultyId IS NULL OR c.faculty_id = :facultyId)
        """,
        nativeQuery = true)
    Page<Course> searchCourses(
            @Param("name") String name,
            @Param("facultyId") Long facultyId,
            Pageable pageable);
}
