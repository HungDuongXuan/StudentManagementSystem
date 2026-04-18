package com.studentmanagement.service;

import com.studentmanagement.dto.request.ClassroomRequestDTO;
import com.studentmanagement.dto.request.search.ClassroomSearchRequestDTO;
import com.studentmanagement.dto.response.ClassroomResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IClassroomService {

    ClassroomResponseDTO createClassroom(ClassroomRequestDTO request);

    List<ClassroomResponseDTO> getAllClassrooms();

    ClassroomResponseDTO getClassroomById(Long id);

    ClassroomResponseDTO updateClassroom(Long id, ClassroomRequestDTO request);

    void deleteClassroom(Long id);

    List<com.studentmanagement.dto.response.StudentResponseDTO> getStudentsByClassroom(Long classroomId);

    Page<ClassroomResponseDTO> searchClassrooms(ClassroomSearchRequestDTO request);
}
