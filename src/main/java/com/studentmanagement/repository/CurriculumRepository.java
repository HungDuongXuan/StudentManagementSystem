package com.studentmanagement.repository;

import com.studentmanagement.entity.Curriculum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

    @Query(value = """
            SELECT DISTINCT c FROM Curriculum c
            JOIN FETCH c.program p
            JOIN FETCH c.major m
            JOIN FETCH c.faculty f
            WHERE (:name IS NULL OR c.name LIKE %:name%)
            AND (:programId IS NULL OR p.id = :programId)
            AND (:majorId IS NULL OR m.id = :majorId)
            AND (:facultyId IS NULL OR f.id = :facultyId)
            """,
            countQuery = """
                    SELECT COUNT(DISTINCT c) FROM Curriculum c
                    LEFT JOIN c.program p
                    LEFT JOIN c.major m
                    LEFT JOIN c.faculty f
                    WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')))
                    AND (:programId IS NULL OR p.id = :programId)
                    AND (:majorId IS NULL OR m.id = :majorId)
                    AND (:facultyId IS NULL OR f.id = :facultyId)
                    """)
    Page<Curriculum> searchCurriculums(
            @Param("name") String name,
            @Param("programId") Long programId,
            @Param("majorId") Long majorId,
            @Param("facultyId") Long facultyId,
            Pageable pageable);

    boolean existsByProgramIdAndMajorIdAndFacultyId(Long programId, Long majorId, Long facultyId);
}
