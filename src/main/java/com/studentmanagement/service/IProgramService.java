package com.studentmanagement.service;

import com.studentmanagement.dto.request.ProgramRequestDTO;
import com.studentmanagement.dto.request.search.ProgramSearchRequestDTO;
import com.studentmanagement.dto.response.ProgramResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IProgramService {
    ProgramResponseDTO createProgram(ProgramRequestDTO programRequestDTO);

    List<ProgramResponseDTO> createProgramsBatch(List<ProgramRequestDTO> programRequestDTOs);

    Page<ProgramResponseDTO> searchPrograms(ProgramSearchRequestDTO programSearchRequestDTO);

    ProgramResponseDTO getProgramById(Long id);

    ProgramResponseDTO updateProgram(Long id, ProgramRequestDTO programRequestDTO);

    void deleteProgram(Long id);
}
