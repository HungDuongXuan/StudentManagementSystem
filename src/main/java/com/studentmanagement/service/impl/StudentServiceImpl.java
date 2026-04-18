package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.StudentRequestDTO;
import com.studentmanagement.dto.request.search.StudentSearchRequestDTO;
import com.studentmanagement.dto.response.StudentResponseDTO;
import com.studentmanagement.entity.Classroom;
import com.studentmanagement.entity.Student;
import com.studentmanagement.entity.type.AccountStatus;
import com.studentmanagement.mapper.StudentMapper;
import com.studentmanagement.repository.ClassroomRepository;
import com.studentmanagement.repository.MajorRepository;
import com.studentmanagement.repository.StudentRepository;
import com.studentmanagement.service.IStudentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements IStudentService {
    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;
    private final MajorRepository majorRepository;
    private final StudentMapper studentMapper;

    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO request) {
        // existsByEmail: 1 cheap EXISTS query
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email '" + request.getEmail() + "' đã được sử dụng.");
        }

        Student student = studentMapper.toEntity(request);
        student.setStatus(AccountStatus.ACTIVE);

        // Auto-generate student code: SV + 2-digit year + 4-digit sequence
        // findMaxSequenceByPrefix: 1 query
        String prefix = "SV" + String.valueOf(LocalDate.now().getYear()).substring(2);
        int maxSeq = studentRepository.findMaxSequenceByPrefix(prefix + "%");
        student.setStudentCode(prefix + String.format("%04d", maxSeq + 1));

        // findAllById cho classrooms: 1 query (IN clause)
        if (request.getClassroomIds() != null && !request.getClassroomIds().isEmpty()) {
            Set<Classroom> classrooms = new HashSet<>(classroomRepository.findAllById(request.getClassroomIds()));
            student.setClassrooms(classrooms);
        }

        // save: 1 INSERT query
        // Tổng: 2-4 queries (existsByEmail + findMax + save + optional findAllById)
        // Trước đó: 5 queries (existsByEmail + findMax + findById(major) + findAllById + save)
        Student saved = studentRepository.save(student);
        log.info("Student created: {} - {}", saved.getStudentCode(), saved.getName());
        return studentMapper.toDto(saved);
    }

    @Override
    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sinh viên không tồn tại với id: " + id));
        return studentMapper.toDto(student);
    }

    @Override
    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentRequestDTO request) {
        // findById cho main entity: cần load đầy đủ vì update = 1 query
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sinh viên không tồn tại với id: " + id));

        studentMapper.updateEntity(request, existing);

        // findAllById: 1 query (IN clause)
        if (request.getClassroomIds() != null) {
            Set<Classroom> classrooms = new HashSet<>(classroomRepository.findAllById(request.getClassroomIds()));
            existing.setClassrooms(classrooms);
        }

        // Tổng: 2-3 queries (findById + save + optional findAllById)
        // Trước đó: 4 queries
        Student updated = studentRepository.save(existing);
        return studentMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Sinh viên không tồn tại với id: " + id));
        student.setStatus(AccountStatus.BANNED);
        studentRepository.save(student);
    }

    @Override
    public Page<StudentResponseDTO> searchStudents(StudentSearchRequestDTO request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        // Chuẩn bị các chuỗi LIKE trước để tránh CONCAT trong SQL
        String nameParam = (request.getName() != null) ? "%" + request.getName() + "%" : null;
        String emailParam = (request.getEmail() != null) ? "%" + request.getEmail() + "%" : null;
        String codeParam = (request.getStudentCode() != null) ? "%" + request.getStudentCode() + "%" : null;

        AccountStatus statusEnum = null;
        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            statusEnum = AccountStatus.valueOf(request.getStatus().toUpperCase());
        }

        Page<Student> page = studentRepository.searchStudents(
                nameParam, emailParam, codeParam,
                request.getClassroomIds(), statusEnum, pageable);

        return page.map(studentMapper::toDto);
    }

    @Override
    @Transactional
    public StudentResponseDTO addClassroomToStudent(Long studentId, Long classroomId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Sinh viên không tồn tại với id: " + studentId));
        // getReferenceById: 0 queries thay vì findById load toàn bộ Classroom
        student.getClassrooms().add(classroomRepository.getReferenceById(classroomId));
        Student updated = studentRepository.save(student);
        return studentMapper.toDto(updated);
    }
}
