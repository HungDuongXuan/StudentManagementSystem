package com.studentmanagement.repository;

import com.studentmanagement.entity.ComponentScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComponentScoreRepository extends JpaRepository<ComponentScore, Long> {

    List<ComponentScore> findByEnrollmentId(Long enrollmentId);

    @Modifying
    @Query(value = """
        INSERT INTO student_management_test.component_scores (enrollment_id, course_component_id, score)
        SELECT :enrollmentId, cc.id, NULL
        FROM student_management_test.course_components cc
        WHERE cc.course_id = :courseId
        ON CONFLICT ON CONSTRAINT component_scores_enrollment_id_course_component_id_key DO NOTHING
        """, nativeQuery = true)
    void bulkInsertScoresForEnrollment(
            @Param("enrollmentId") Long enrollmentId,
            @Param("courseId") Long courseId);

    @Modifying
    @Query(value = """
        INSERT INTO student_management_test.component_scores (enrollment_id, course_component_id, score)
        SELECT e.id, :componentId, NULL
        FROM student_management_test.enrollments e
        WHERE e.course_id = :courseId
        ON CONFLICT ON CONSTRAINT component_scores_enrollment_id_course_component_id_key DO NOTHING
        """, nativeQuery = true)
    void bulkInsertScoresForNewComponent(
            @Param("componentId") Long componentId,
            @Param("courseId") Long courseId);

    @Modifying
    @Query(value = """
        INSERT INTO student_management_test.component_scores (enrollment_id, course_component_id, score)
        VALUES (:enrollmentId, :componentId, :score)
        ON CONFLICT ON CONSTRAINT component_scores_enrollment_id_course_component_id_key 
        DO UPDATE SET score = EXCLUDED.score
        """, nativeQuery = true)
    void upsertScore(
            @Param("enrollmentId") Long enrollmentId,
            @Param("componentId") Long componentId,
            @Param("score") Double score);

    @Modifying
    @Query("DELETE FROM ComponentScore cs WHERE cs.courseComponent.id = :componentId")
    void deleteAllByCourseComponentId(@Param("componentId") Long componentId);

    @Query(value = """
        SELECT CASE
            WHEN COUNT(*) = 0 THEN NULL
            WHEN COUNT(cs.score) < COUNT(*) THEN NULL
            WHEN ABS(SUM(cc.weight) - 1.0) > 0.001 THEN NULL
            ELSE ROUND(CAST(SUM(cs.score * cc.weight) AS NUMERIC), 2)
        END
        FROM student_management_test.component_scores cs
        JOIN student_management_test.course_components cc ON cs.course_component_id = cc.id
        WHERE cs.enrollment_id = :enrollmentId
        """, nativeQuery = true)
    Double calculateFinalScore(@Param("enrollmentId") Long enrollmentId);
}
