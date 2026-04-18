package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.CampusRequestDTO;
import com.studentmanagement.dto.response.CampusResponseDTO;
import com.studentmanagement.entity.Campus;
import com.studentmanagement.entity.type.OperationalStatus;
import com.studentmanagement.mapper.CampusMapper;
import com.studentmanagement.repository.CampusRepository;
import com.studentmanagement.service.ICampusService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CampusServiceImpl implements ICampusService {
    private final CampusRepository campusRepository;
    private final CampusMapper campusMapper;

    @Override
    @Transactional
    public CampusResponseDTO createCampus(CampusRequestDTO request) {
        log.info("Creating campus: {}", request.getName());
        Campus campus = campusMapper.toEntity(request);
        campus.setStatus(OperationalStatus.ACTIVE);
        Campus saved = campusRepository.save(campus);
        log.info("Campus created with ID: {}", saved.getId());
        return campusMapper.toDto(saved);
    }

    @Override
    public CampusResponseDTO getCampusById(Long id) {
        Campus campus = campusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campus không tồn tại với id: " + id));
        return campusMapper.toDto(campus);
    }

    @Override
    @Transactional
    public CampusResponseDTO updateCampus(Long id, CampusRequestDTO request) {
        Campus existing = campusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campus không tồn tại với id: " + id));
        campusMapper.updateEntity(request, existing);
        Campus updated = campusRepository.save(existing);
        return campusMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteCampus(Long id) {
        Campus campus = campusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campus không tồn tại với id: " + id));
        campusRepository.delete(campus);
    }

    @Override
    public List<CampusResponseDTO> getAllCampuses() {
        return campusMapper.toDto(campusRepository.findAll());
    }
}
