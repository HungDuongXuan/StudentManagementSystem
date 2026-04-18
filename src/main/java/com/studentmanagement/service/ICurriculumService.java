package com.studentmanagement.service;

import com.studentmanagement.dto.request.CurriculumRequestDTO;
import com.studentmanagement.dto.request.search.CurriculumSearchRequestDTO;
import com.studentmanagement.dto.response.CurriculumResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICurriculumService {
    public CurriculumResponseDTO createCurriculum(CurriculumRequestDTO request);

    public List<CurriculumResponseDTO> createCurriculumsBatch(List<CurriculumRequestDTO> requests);

    Page<CurriculumResponseDTO> searchCurriculums(CurriculumSearchRequestDTO request);

    CurriculumResponseDTO getCurriculumById(Long id);

    CurriculumResponseDTO updateCurriculum(Long id, CurriculumRequestDTO request);

    void deleteCurriculum(Long id);
}
