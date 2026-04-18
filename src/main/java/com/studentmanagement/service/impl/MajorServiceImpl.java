package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.MajorRequestDTO;
import com.studentmanagement.dto.request.search.MajorSearchRequestDTO;
import com.studentmanagement.dto.response.MajorResponseDTO;
import com.studentmanagement.entity.Major;
import com.studentmanagement.entity.type.OperationalStatus;
import com.studentmanagement.mapper.MajorMapper;
import com.studentmanagement.repository.MajorRepository;
import com.studentmanagement.service.IMajorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MajorServiceImpl implements IMajorService {
    private final MajorMapper majorMapper;
    private final MajorRepository majorRepository;

    @Override
    @Transactional
    public MajorResponseDTO createMajor(MajorRequestDTO request) {
        if (majorRepository.existsByMajorCode(request.getMajorCode())) {
            throw new IllegalArgumentException("Mã ngành '" + request.getMajorCode() + "' đã tồn tại.");
        }
        Major major = majorMapper.toEntity(request);
        major.setStatus(OperationalStatus.ACTIVE);
        Major saved = majorRepository.save(major);
        return majorMapper.toDto(saved);
    }

    @Override
    @Transactional
    public List<MajorResponseDTO> createMajorsBatch(List<MajorRequestDTO> requests) {
        List<Major> majors = requests.stream().map(req -> {
            Major major = majorMapper.toEntity(req);
            major.setStatus(OperationalStatus.ACTIVE);
            return major;
        }).toList();
        return majorRepository.saveAll(majors).stream().map(majorMapper::toDto).toList();
    }

    @Override
    public Page<MajorResponseDTO> searchMajors(MajorSearchRequestDTO request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Major> page = majorRepository.searchMajors(request.getName(), request.getMajorCode(), pageable);
        return page.map(majorMapper::toDto);
    }

    @Override
    public MajorResponseDTO getMajorById(Long id) {
        Major major = majorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ngành không tồn tại với id: " + id));
        return majorMapper.toDto(major);
    }

    @Override
    @Transactional
    public MajorResponseDTO updateMajor(Long id, MajorRequestDTO request) {
        Major existing = majorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ngành không tồn tại với id: " + id));
        majorMapper.updateEntity(request, existing);
        Major updated = majorRepository.save(existing);
        return majorMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteMajor(Long id) {
        if (!majorRepository.existsById(id)) {
            throw new IllegalArgumentException("Ngành không tồn tại với id: " + id);
        }
        majorRepository.deleteById(id);
    }
}
