package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.ClassroomRequestDTO;
import com.studentmanagement.dto.request.search.ClassroomSearchRequestDTO;
import com.studentmanagement.dto.response.ClassroomResponseDTO;
import com.studentmanagement.dto.response.StudentResponseDTO;
import com.studentmanagement.entity.Classroom;
import com.studentmanagement.entity.type.OperationalStatus;
import com.studentmanagement.mapper.ClassroomMapper;
import com.studentmanagement.mapper.StudentMapper;
import com.studentmanagement.repository.ClassroomRepository;
import com.studentmanagement.repository.CurriculumRepository;
import com.studentmanagement.service.IClassroomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomServiceImpl implements IClassroomService {
    private final ClassroomRepository classroomRepository;
    private final CurriculumRepository curriculumRepository;
    private final ClassroomMapper classroomMapper;
    private final StudentMapper studentMapper;

    @Override
    @Transactional
    public ClassroomResponseDTO createClassroom(ClassroomRequestDTO request) {
        Classroom classroom = classroomMapper.toEntity(request);
        // getReferenceById: 0 queries
        classroom.setCurriculum(curriculumRepository.getReferenceById(request.getCurriculumId()));
        classroom.setStatus(OperationalStatus.ACTIVE);
        Classroom saved = classroomRepository.save(classroom);
        return classroomMapper.toDto(saved);
    }

    @Override
    public List<ClassroomResponseDTO> getAllClassrooms() {
        return classroomMapper.toDto(classroomRepository.findAll());
    }

    @Override
    public ClassroomResponseDTO getClassroomById(Long id) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lớp không tồn tại với id: " + id));
        return classroomMapper.toDto(classroom);
    }

    @Override
    @Transactional
    public ClassroomResponseDTO updateClassroom(Long id, ClassroomRequestDTO request) {
        Classroom existing = classroomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Lớp không tồn tại với id: " + id));

        classroomMapper.updateEntity(request, existing);
        // getReferenceById: 0 queries
        existing.setCurriculum(curriculumRepository.getReferenceById(request.getCurriculumId()));
        Classroom updated = classroomRepository.save(existing);
        return classroomMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteClassroom(Long id) {
        if (!classroomRepository.existsById(id)) {
            throw new IllegalArgumentException("Lớp không tồn tại với id: " + id);
        }
        classroomRepository.deleteById(id);
    }

    @Override
    public List<StudentResponseDTO> getStudentsByClassroom(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new IllegalArgumentException("Lớp không tồn tại với id: " + classroomId));
        return classroom.getStudents().stream().map(studentMapper::toDto).toList();
    }

    @Override
    public Page<ClassroomResponseDTO> searchClassrooms(ClassroomSearchRequestDTO request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Classroom> page = classroomRepository.searchClassrooms(request.getName(), request.getCurriculumId(), pageable);
        return page.map(classroomMapper::toDto);
    }
}
