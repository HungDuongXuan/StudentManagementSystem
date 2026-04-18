package com.studentmanagement.service;

import com.studentmanagement.dto.request.ComponentScoreRequestDTO;
import com.studentmanagement.dto.request.EnrollmentRequestDTO;
import com.studentmanagement.dto.response.EnrollmentResponseDTO;

import java.util.List;

public interface IEnrollmentService {
    List<EnrollmentResponseDTO> getAll();
    EnrollmentResponseDTO getById(Long id);
    List<EnrollmentResponseDTO> getByStudentId(Long studentId);
    List<EnrollmentResponseDTO> getByCourseId(Long courseId);
    EnrollmentResponseDTO create(EnrollmentRequestDTO request);
    EnrollmentResponseDTO updateScores(Long enrollmentId, List<ComponentScoreRequestDTO> scores);
    void delete(Long id);
}
