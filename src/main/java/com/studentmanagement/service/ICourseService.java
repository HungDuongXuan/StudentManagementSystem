package com.studentmanagement.service;

import com.studentmanagement.dto.request.CourseRequestDTO;
import com.studentmanagement.dto.request.search.CourseSearchRequestDTO;
import com.studentmanagement.dto.response.CourseResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICourseService {

    CourseResponseDTO createCourse(CourseRequestDTO request);

    List<CourseResponseDTO> getAllCourses();

    CourseResponseDTO getCourseById(Long id);

    CourseResponseDTO updateCourse(Long id, CourseRequestDTO request);

    void deleteCourse(Long id);

    List<CourseResponseDTO> getCoursesByFacultyId(Long facultyId);

    Page<CourseResponseDTO> searchCourses(CourseSearchRequestDTO request);

    List<CourseResponseDTO> createCoursesBatch(List<CourseRequestDTO> requests);
}
