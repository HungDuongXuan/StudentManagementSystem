package com.studentmanagement.repository;

import com.studentmanagement.entity.Program;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    boolean existsByName(String name);
    boolean existsByProgramCode(String programCode);

    @Query(value = """
    SELECT DISTINCT p FROM Program p
    LEFT JOIN FETCH p.campuses c
    WHERE (:name IS NULL OR p.name LIKE %:name%)
    AND (:programCode IS NULL OR p.programCode LIKE %:programCode%)
    AND (:campusId IS NULL OR EXISTS (
        SELECT 1 FROM p.campuses campus_sub WHERE campus_sub.id = :campusId
    ))
    """,
    countQuery = """
    SELECT COUNT(DISTINCT p) FROM Program p
    LEFT JOIN p.campuses c
    where (:name IS NULL OR p.name LIKE %:name%)
    AND (:programCode IS NULL OR p.programCode LIKE %:programCode%)
    AND (:campusId IS NULL OR c.id = :campusId)
    """)
    Page<Program> searchPrograms(
            @Param("name") String name,
            @Param("programCode") String programCode,
            @Param("campusId") Long campusId,
            Pageable pageable);
}
