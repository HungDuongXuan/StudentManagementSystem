package com.studentmanagement.service;

import com.studentmanagement.dto.request.MajorRequestDTO;
import com.studentmanagement.dto.request.search.MajorSearchRequestDTO;
import com.studentmanagement.dto.response.MajorResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMajorService {
    MajorResponseDTO createMajor(MajorRequestDTO  request);

    List<MajorResponseDTO> createMajorsBatch(List<MajorRequestDTO> requests);

    Page<MajorResponseDTO> searchMajors(MajorSearchRequestDTO request);

    MajorResponseDTO getMajorById(Long id);

    MajorResponseDTO updateMajor(Long id, MajorRequestDTO request);

    void deleteMajor(Long id);
}
