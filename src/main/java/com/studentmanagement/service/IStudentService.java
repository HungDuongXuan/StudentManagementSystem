package com.studentmanagement.service;

import com.studentmanagement.dto.request.StudentRequestDTO;
import com.studentmanagement.dto.request.search.StudentSearchRequestDTO;
import com.studentmanagement.dto.response.StudentResponseDTO;
import org.springframework.data.domain.Page;

public interface IStudentService {
    StudentResponseDTO createStudent(StudentRequestDTO request);
    StudentResponseDTO getStudentById(Long id);
    StudentResponseDTO updateStudent(Long id, StudentRequestDTO request);
    void deleteStudent(Long id);
    Page<StudentResponseDTO> searchStudents(StudentSearchRequestDTO request);
    StudentResponseDTO addClassroomToStudent(Long studentId, Long classroomId);
}
