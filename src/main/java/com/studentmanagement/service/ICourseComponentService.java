package com.studentmanagement.service;

import com.studentmanagement.dto.request.CourseComponentRequestDTO;
import com.studentmanagement.dto.response.CourseComponentResponseDTO;

import java.util.List;

public interface ICourseComponentService {
    List<CourseComponentResponseDTO> getByCourseId(Long courseId);
    CourseComponentResponseDTO getById(Long id);
    CourseComponentResponseDTO create(Long courseId, CourseComponentRequestDTO request);
    CourseComponentResponseDTO update(Long id, CourseComponentRequestDTO request);
    void delete(Long id);
}
