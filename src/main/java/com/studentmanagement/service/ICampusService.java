package com.studentmanagement.service;

import com.studentmanagement.dto.request.CampusRequestDTO;
import com.studentmanagement.dto.response.CampusResponseDTO;

import java.util.List;

public interface ICampusService {
    CampusResponseDTO createCampus(CampusRequestDTO request);
    CampusResponseDTO getCampusById(Long id);
    CampusResponseDTO updateCampus(Long id, CampusRequestDTO request);
    void deleteCampus(Long id);
    List<CampusResponseDTO> getAllCampuses();
}
