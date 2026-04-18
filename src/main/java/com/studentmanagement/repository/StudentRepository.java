package com.studentmanagement.repository;

import com.studentmanagement.entity.Student;
import com.studentmanagement.entity.type.AccountStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    @Query(value = """
    SELECT DISTINCT s FROM Student s
    LEFT JOIN s.classrooms c
    WHERE (:name IS NULL OR LOWER(s.name) LIKE :name)
    AND (:email IS NULL OR LOWER(s.email) LIKE :email)
    AND (:studentCode IS NULL OR LOWER(s.studentCode) LIKE :studentCode)
    AND (:classroomIds IS NULL OR c.id IN :classroomIds)
    AND (:status IS NULL OR s.status = :status)
    """,
            countQuery = """
    SELECT COUNT(DISTINCT s) FROM Student s
    LEFT JOIN s.classrooms c
    WHERE (:name IS NULL OR LOWER(s.name) LIKE :name)
    AND (:email IS NULL OR LOWER(s.email) LIKE :email)
    AND (:studentCode IS NULL OR LOWER(s.studentCode) LIKE :studentCode)
    AND (:classroomIds IS NULL OR c.id IN :classroomIds)
    AND (:status IS NULL OR s.status = :status)
    """)
    Page<Student> searchStudents(
            @Param("name") String name,
            @Param("email") String email,
            @Param("studentCode") String studentCode,
            @Param("classroomIds") List<Long> classroomIds,
            @Param("status") AccountStatus status,
            Pageable pageable);

    @Query(value = "SELECT COALESCE(MAX(CAST(SUBSTRING(student_code, 3) AS INTEGER)), 0) " +
            "FROM students WHERE student_code LIKE :prefix",
            nativeQuery = true)
    int findMaxSequenceByPrefix(@Param("prefix") String prefix);
}
