package com.studentmanagement.repository;

import com.studentmanagement.entity.Faculty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    boolean existsByName(String name);
    boolean existsByNameAndCampusId(String name, Long campusId);

    List<Faculty> findAllByNameInAndCampusIdIn(Set<String> names, Set<Long> campusIds);

    @Query(value = """
        SELECT f.* FROM student_management_test.faculties f
        JOIN student_management_test.campuses c ON f.campus_id = c.id
        WHERE (:name IS NULL OR f.name ILIKE CONCAT('%', :name, '%'))
        AND (:campusId IS NULL OR f.campus_id = :campusId)
        ORDER BY f.created_at DESC
        """,
        countQuery = """
        SELECT COUNT(*) FROM student_management_test.faculties f
        JOIN student_management_test.campuses c ON f.campus_id = c.id
        WHERE (:name IS NULL OR f.name ILIKE CONCAT('%', :name, '%'))
        AND (:campusId IS NULL OR f.campus_id = :campusId)
        """,
        nativeQuery = true)
    Page<Faculty> searchFaculties(
            @Param("name") String name,
            @Param("campusId") Long campusId,
            Pageable pageable);
}
