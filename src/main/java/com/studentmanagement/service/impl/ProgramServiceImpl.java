package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.ProgramRequestDTO;
import com.studentmanagement.dto.request.search.ProgramSearchRequestDTO;
import com.studentmanagement.dto.response.ProgramResponseDTO;
import com.studentmanagement.entity.Campus;
import com.studentmanagement.entity.Program;
import com.studentmanagement.entity.type.OperationalStatus;
import com.studentmanagement.mapper.ProgramMapper;
import com.studentmanagement.repository.CampusRepository;
import com.studentmanagement.repository.ProgramRepository;
import com.studentmanagement.service.IProgramService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements IProgramService {
    private final ProgramMapper programMapper;
    private final ProgramRepository programRepository;
    private final CampusRepository campusRepository;

    @Override
    @Transactional
    public ProgramResponseDTO createProgram(ProgramRequestDTO request) {
        Program program = programMapper.toEntity(request);
        program.setStatus(OperationalStatus.ACTIVE);

        Program saved = programRepository.save(program);

        if (request.getCampusIds() != null && !request.getCampusIds().isEmpty()) {
            List<Campus> campuses = campusRepository.findAllById(request.getCampusIds());
            saved.setCampuses(campuses);
            for (Campus campus : campuses) {
                if (campus.getPrograms() == null) {
                    campus.setPrograms(new java.util.ArrayList<>());
                }
                if (!campus.getPrograms().contains(saved)) {
                    campus.getPrograms().add(saved);
                }
            }
            campusRepository.saveAll(campuses);
        }

        return programMapper.toDto(saved);
    }

    @Override
    @Transactional
    public List<ProgramResponseDTO> createProgramsBatch(List<ProgramRequestDTO> requests) {
        List<Program> programs = requests.stream().map(dto -> {
            Program program = programMapper.toEntity(dto);
            program.setStatus(OperationalStatus.ACTIVE);
            Program savedProgram = programRepository.save(program);
            
            if (dto.getCampusIds() != null && !dto.getCampusIds().isEmpty()) {
                List<Campus> campuses = campusRepository.findAllById(dto.getCampusIds());
                savedProgram.setCampuses(campuses);
                for (Campus campus : campuses) {
                    if (campus.getPrograms() == null) {
                        campus.setPrograms(new java.util.ArrayList<>());
                    }
                    if (!campus.getPrograms().contains(savedProgram)) {
                        campus.getPrograms().add(savedProgram);
                    }
                }
                campusRepository.saveAll(campuses);
            }
            return savedProgram;
        }).toList();

        return programRepository.saveAll(programs).stream().map(programMapper::toDto).toList();
    }

    @Override
    public Page<ProgramResponseDTO> searchPrograms(ProgramSearchRequestDTO request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Program> page = programRepository.searchPrograms(
                request.getName(), request.getProgramCode(), request.getCampusId(), pageable);
        return page.map(programMapper::toDto);
    }

    @Override
    public ProgramResponseDTO getProgramById(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chương trình không tồn tại với id: " + id));
        return programMapper.toDto(program);
    }

    @Override
    @Transactional
    public ProgramResponseDTO updateProgram(Long id, ProgramRequestDTO request) {
        Program existing = programRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Chương trình không tồn tại với id: " + id));

        programMapper.updateEntity(request, existing);

        Program updated = programRepository.save(existing);

        if (request.getCampusIds() != null) {
            if (existing.getCampuses() != null) {
                for (Campus oldCampus : existing.getCampuses()) {
                    if (oldCampus.getPrograms() != null) {
                        oldCampus.getPrograms().remove(updated);
                    }
                }
                campusRepository.saveAll(existing.getCampuses());
            }

            List<Campus> campuses = campusRepository.findAllById(request.getCampusIds());
            updated.setCampuses(campuses);

            for (Campus campus : campuses) {
                if (campus.getPrograms() == null) {
                    campus.setPrograms(new java.util.ArrayList<>());
                }
                if (!campus.getPrograms().contains(updated)) {
                    campus.getPrograms().add(updated);
                }
            }
            campusRepository.saveAll(campuses);
        }

        return programMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteProgram(Long id) {
        if (!programRepository.existsById(id)) {
            throw new IllegalArgumentException("Chương trình không tồn tại với id: " + id);
        }
        programRepository.deleteById(id);
    }
}
