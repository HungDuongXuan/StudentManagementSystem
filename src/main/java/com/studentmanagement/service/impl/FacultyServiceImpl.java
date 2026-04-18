package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.FacultyRequestDTO;
import com.studentmanagement.dto.request.search.FacultySearchRequestDTO;
import com.studentmanagement.dto.response.FacultyResponseDTO;
import com.studentmanagement.entity.Faculty;
import com.studentmanagement.entity.type.OperationalStatus;
import com.studentmanagement.mapper.FacultyMapper;
import com.studentmanagement.repository.CampusRepository;
import com.studentmanagement.repository.FacultyRepository;
import com.studentmanagement.service.IFacultyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements IFacultyService {
    private final FacultyMapper facultyMapper;
    private final FacultyRepository facultyRepository;
    private final CampusRepository campusRepository;

    /**
     * TỐI ƯU: existsByNameAndCampusId + save = 2 queries
     * TRƯỚC: existsBy + findById(campus) + save = 3 queries
     */
    @Override
    @Transactional
    public FacultyResponseDTO createFaculty(FacultyRequestDTO request) {
        if (facultyRepository.existsByNameAndCampusId(request.getName(), request.getCampusId())) {
            throw new IllegalArgumentException("Khoa '" + request.getName() + "' đã tồn tại trong campus này.");
        }

        Faculty faculty = Faculty.builder()
                .name(request.getName())
                .campus(campusRepository.getReferenceById(request.getCampusId()))
                .status(OperationalStatus.ACTIVE)
                .build();

        Faculty saved = facultyRepository.save(faculty);
        return facultyMapper.toDto(saved);
    }

    @Override
    public Page<FacultyResponseDTO> searchFaculties(FacultySearchRequestDTO request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Faculty> page = facultyRepository.searchFaculties(request.getName(), request.getCampusId(), pageable);
        return page.map(facultyMapper::toDto);
    }

    @Override
    @Transactional
    public List<FacultyResponseDTO> createFacultiesBatch(List<FacultyRequestDTO> requests) {
        Set<String> processedNames = new HashSet<>();
        List<Faculty> toSave = new ArrayList<>();

        for (FacultyRequestDTO req : requests) {
            if (!processedNames.add(req.getName() + "_" + req.getCampusId())) {
                throw new IllegalArgumentException("Trùng lặp trong request: '" + req.getName() + "'");
            }
            if (facultyRepository.existsByNameAndCampusId(req.getName(), req.getCampusId())) {
                throw new IllegalArgumentException("Khoa '" + req.getName() + "' đã tồn tại.");
            }
            toSave.add(Faculty.builder()
                    .name(req.getName())
                    .campus(campusRepository.getReferenceById(req.getCampusId()))
                    .status(OperationalStatus.ACTIVE)
                    .build());
        }
        // batch INSERT nhờ batch_size=30
        return facultyRepository.saveAll(toSave).stream().map(facultyMapper::toDto).toList();
    }

    @Override
    public FacultyResponseDTO getFacultyById(Long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Khoa không tồn tại với id: " + id));
        return facultyMapper.toDto(faculty);
    }

    @Override
    @Transactional
    public FacultyResponseDTO updateFaculty(Long id, FacultyRequestDTO request) {
        Faculty existing = facultyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Khoa không tồn tại với id: " + id));

        existing.setName(request.getName());
        existing.setCampus(campusRepository.getReferenceById(request.getCampusId()));
        if(request.getStatus().equalsIgnoreCase("ACTIVE") || request.getStatus().equalsIgnoreCase("INACTIVE") ) {
            existing.setStatus(OperationalStatus.valueOf(request.getStatus()));
        }
        Faculty updated = facultyRepository.save(existing);
        return facultyMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteFaculty(Long id) {
        if (!facultyRepository.existsById(id)) {
            throw new IllegalArgumentException("Khoa không tồn tại với id: " + id);
        }
        facultyRepository.deleteById(id);
    }
}
