package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.ComponentScoreRequestDTO;
import com.studentmanagement.dto.request.EnrollmentRequestDTO;
import com.studentmanagement.dto.response.EnrollmentResponseDTO;
import com.studentmanagement.entity.ComponentScore;
import com.studentmanagement.entity.Enrollment;
import com.studentmanagement.mapper.EnrollmentMapper;
import com.studentmanagement.repository.*;
import com.studentmanagement.service.IEnrollmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements IEnrollmentService {
    private final EnrollmentMapper enrollmentMapper;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ComponentScoreRepository componentScoreRepository;

    @Override
    public List<EnrollmentResponseDTO> getAll() {
        return enrollmentMapper.toDto(enrollmentRepository.findAll());
    }

    @Override
    public EnrollmentResponseDTO getById(Long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Đăng ký không tồn tại với id: " + id));
        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    public List<EnrollmentResponseDTO> getByStudentId(Long studentId) {
        return enrollmentMapper.toDto(enrollmentRepository.findByStudentId(studentId));
    }

    @Override
    public List<EnrollmentResponseDTO> getByCourseId(Long courseId) {
        return enrollmentMapper.toDto(enrollmentRepository.findByCourseId(courseId));
    }

    /**
     * TỐI ƯU: Tạo enrollment + auto-create component scores
     *
     * TRƯỚC: 6 queries
     *   1. existsByStudentIdAndCourseId
     *   2. findById(student)
     *   3. findById(course)
     *   4. save(enrollment)
     *   5. findByCourseId(components)
     *   6. saveAll(N scores) = N INSERT queries
     *
     * SAU: 3 queries
     *   1. existsByStudentIdAndCourseId
     *   2. save(enrollment) - dùng getReferenceById cho student/course = 0 query
     *   3. bulkInsertScoresForEnrollment - 1 native INSERT...SELECT thay vì N saves
     */
    @Override
    @Transactional
    public EnrollmentResponseDTO create(EnrollmentRequestDTO request) {
        if (enrollmentRepository.existsByStudentIdAndCourseId(request.getStudentId(), request.getCourseId())) {
            throw new IllegalArgumentException("Sinh viên đã đăng ký môn học này.");
        }

        // Tạo Enrollment trực tiếp bằng Native SQL và trả về ID
        Long savedId = enrollmentRepository.insertEnrollmentNative(request.getStudentId(), request.getCourseId());

        // Native SQL: Tự tạo tất cả component_scores cho enrollment mới trong 1 câu truy vấn
        componentScoreRepository.bulkInsertScoresForEnrollment(savedId, request.getCourseId());

        // Mở lại để load toàn bộ relation properties trả về cho user UI
        Enrollment saved = enrollmentRepository.findById(savedId)
                .orElseThrow(() -> new IllegalArgumentException("Lỗi dữ liệu sau khi đăng ký Native SQL"));

        return enrollmentMapper.toDto(saved);
    }

    /**
     * TỐI ƯU: Nhập điểm
     */
    @Override
    @Transactional
    public EnrollmentResponseDTO updateScores(Long enrollmentId, List<ComponentScoreRequestDTO> scores) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new IllegalArgumentException("Đăng ký không tồn tại với id: " + enrollmentId));

        // Sử dụng Native SQL Upsert để thêm điểm nhanh chóng cho enrollment này
        for (ComponentScoreRequestDTO dto : scores) {
            componentScoreRepository.upsertScore(enrollmentId, dto.getCourseComponentId(), dto.getScore());
        }

        // Native SQL: Tính điểm tổng kết chỉ với 1 câu query duy nhất đẩy về DB xử lí
        Double finalScore = componentScoreRepository.calculateFinalScore(enrollmentId);
        enrollment.setFinalScore(finalScore);
        
        // Cập nhật lại final_score mới vào DB
        enrollmentRepository.save(enrollment);

        return enrollmentMapper.toDto(enrollment);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Đăng ký không tồn tại với id: " + id);
        }
        enrollmentRepository.deleteById(id);
    }
}
