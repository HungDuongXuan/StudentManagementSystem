package com.studentmanagement.repository;

import com.studentmanagement.entity.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MajorRepository extends JpaRepository<Major, Long> {

    boolean existsByName(String name);
    boolean existsByMajorCode(String majorCode);

    @Query(value = """
        SELECT * FROM student_management_test.majors m
        WHERE m.status != 'DELETED'
        AND (:name IS NULL OR m.name ILIKE CONCAT('%', :name, '%'))
        AND (:majorCode IS NULL OR m.major_code ILIKE CONCAT('%', :majorCode, '%'))
        ORDER BY m.created_at DESC
        """,
        countQuery = """
        SELECT COUNT(*) FROM student_management_test.majors m
        WHERE m.status != 'DELETED'
        AND (:name IS NULL OR m.name ILIKE CONCAT('%', :name, '%'))
        AND (:majorCode IS NULL OR m.major_code ILIKE CONCAT('%', :majorCode, '%'))
        """,
        nativeQuery = true)
    Page<Major> searchMajors(
            @Param("name") String name,
            @Param("majorCode") String majorCode,
            Pageable pageable);
}
