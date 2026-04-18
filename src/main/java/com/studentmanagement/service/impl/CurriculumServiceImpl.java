package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.CurriculumRequestDTO;
import com.studentmanagement.dto.request.search.CurriculumSearchRequestDTO;
import com.studentmanagement.dto.response.CurriculumResponseDTO;
import com.studentmanagement.entity.Curriculum;
import com.studentmanagement.entity.type.OperationalStatus;
import com.studentmanagement.mapper.CurriculumMapper;
import com.studentmanagement.repository.CurriculumRepository;
import com.studentmanagement.repository.FacultyRepository;
import com.studentmanagement.repository.MajorRepository;
import com.studentmanagement.repository.ProgramRepository;
import com.studentmanagement.service.ICurriculumService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurriculumServiceImpl implements ICurriculumService {
    private final CurriculumMapper curriculumMapper;
    private final CurriculumRepository curriculumRepository;
    private final ProgramRepository programRepository;
    private final MajorRepository majorRepository;
    private final FacultyRepository facultyRepository;

    /**
     * TỐI ƯU:
     * TRƯỚC: existsBy + findById(program) + findById(major) + findById(faculty) + save = 5 queries
     * SAU: existsBy + save = 2 queries (getReferenceById cho 3 FK = 0 query)
     */
    @Override
    @Transactional
    public CurriculumResponseDTO createCurriculum(CurriculumRequestDTO request) {
        if (curriculumRepository.existsByProgramIdAndMajorIdAndFacultyId(
                request.getProgramId(), request.getMajorId(), request.getFacultyId())) {
            throw new IllegalArgumentException("CTĐT với tổ hợp này đã tồn tại.");
        }

        Curriculum curriculum = curriculumMapper.toEntity(request);
        // 3 x getReferenceById = 0 queries (proxy only)
        curriculum.setProgram(programRepository.getReferenceById(request.getProgramId()));
        curriculum.setMajor(majorRepository.getReferenceById(request.getMajorId()));
        curriculum.setFaculty(facultyRepository.getReferenceById(request.getFacultyId()));
        curriculum.setStatus(OperationalStatus.ACTIVE);

        Curriculum saved = curriculumRepository.save(curriculum);
        return curriculumMapper.toDto(saved);
    }

    @Override
    @Transactional
    public List<CurriculumResponseDTO> createCurriculumsBatch(List<CurriculumRequestDTO> requests) {
        List<Curriculum> curriculums = requests.stream().map(req -> {
            Curriculum c = curriculumMapper.toEntity(req);
            // getReferenceById: 0 queries mỗi FK
            c.setProgram(programRepository.getReferenceById(req.getProgramId()));
            c.setMajor(majorRepository.getReferenceById(req.getMajorId()));
            c.setFaculty(facultyRepository.getReferenceById(req.getFacultyId()));
            c.setStatus(OperationalStatus.ACTIVE);
            return c;
        }).toList();
        // saveAll batch INSERT nhờ hibernate.jdbc.batch_size=30
        return curriculumRepository.saveAll(curriculums).stream()
                .map(curriculumMapper::toDto).toList();
    }

    @Override
    public Page<CurriculumResponseDTO> searchCurriculums(CurriculumSearchRequestDTO request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Curriculum> page = curriculumRepository.searchCurriculums(
                request.getName(), request.getProgramId(), request.getMajorId(), request.getFacultyId(), pageable);
        return page.map(curriculumMapper::toDto);
    }

    @Override
    public CurriculumResponseDTO getCurriculumById(Long id) {
        Curriculum curriculum = curriculumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CTĐT không tồn tại với id: " + id));
        return curriculumMapper.toDto(curriculum);
    }

    /**
     * TỐI ƯU: findById + save = 2 queries (getReferenceById cho 3 FK)
     * TRƯỚC: findById + findById(p) + findById(m) + findById(f) + save = 5 queries
     */
    @Override
    @Transactional
    public CurriculumResponseDTO updateCurriculum(Long id, CurriculumRequestDTO request) {
        Curriculum existing = curriculumRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CTĐT không tồn tại với id: " + id));

        curriculumMapper.updateEntity(request, existing);
        existing.setProgram(programRepository.getReferenceById(request.getProgramId()));
        existing.setMajor(majorRepository.getReferenceById(request.getMajorId()));
        existing.setFaculty(facultyRepository.getReferenceById(request.getFacultyId()));

        Curriculum updated = curriculumRepository.save(existing);
        return curriculumMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteCurriculum(Long id) {
        if (!curriculumRepository.existsById(id)) {
            throw new IllegalArgumentException("CTĐT không tồn tại với id: " + id);
        }
        curriculumRepository.deleteById(id);
    }
}
