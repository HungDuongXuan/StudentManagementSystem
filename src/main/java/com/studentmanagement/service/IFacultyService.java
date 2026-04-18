package com.studentmanagement.service;

import com.studentmanagement.dto.request.FacultyRequestDTO;
import com.studentmanagement.dto.request.search.FacultySearchRequestDTO;
import com.studentmanagement.dto.response.FacultyResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IFacultyService {
    FacultyResponseDTO createFaculty(FacultyRequestDTO request);
    FacultyResponseDTO getFacultyById(Long id);
    FacultyResponseDTO updateFaculty(Long id, FacultyRequestDTO request);
    void deleteFaculty(Long id);
    Page<FacultyResponseDTO> searchFaculties(FacultySearchRequestDTO request);
    List<FacultyResponseDTO> createFacultiesBatch(List<FacultyRequestDTO> requests);
}
