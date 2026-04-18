package com.studentmanagement.repository;

import com.studentmanagement.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusRepository extends JpaRepository<Campus, Long> {
    boolean existsByCampusCode(String campusCode);
    boolean existsByName(String name);
}
