package com.studentmanagement.repository;

import com.studentmanagement.entity.Classroom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {

    boolean existsByName(String name);

    @Query(value = """
        SELECT cl.* FROM classrooms cl
        JOIN program_majors pm ON cl.curriculum_id = pm.id
        WHERE cl.status != 'DELETED'
        AND (:name IS NULL OR cl.name ILIKE CONCAT('%', :name, '%'))
        AND (:curriculumId IS NULL OR cl.curriculum_id = :curriculumId)
        ORDER BY cl.created_at DESC
        """,
        countQuery = """
        SELECT COUNT(*) FROM classrooms cl
        WHERE cl.status != 'DELETED'
        AND (:name IS NULL OR cl.name ILIKE CONCAT('%', :name, '%'))
        AND (:curriculumId IS NULL OR cl.curriculum_id = :curriculumId)
        """,
        nativeQuery = true)
    Page<Classroom> searchClassrooms(
            @Param("name") String name,
            @Param("curriculumId") Long curriculumId,
            Pageable pageable);
}
