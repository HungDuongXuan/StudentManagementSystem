package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.CourseComponentRequestDTO;
import com.studentmanagement.dto.response.CourseComponentResponseDTO;
import com.studentmanagement.entity.CourseComponent;
import com.studentmanagement.mapper.CourseComponentMapper;
import com.studentmanagement.repository.ComponentScoreRepository;
import com.studentmanagement.repository.CourseComponentRepository;
import com.studentmanagement.repository.CourseRepository;
import com.studentmanagement.service.ICourseComponentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseComponentServiceImpl implements ICourseComponentService {
    private final CourseComponentMapper courseComponentMapper;
    private final CourseComponentRepository courseComponentRepository;
    private final CourseRepository courseRepository;
    private final ComponentScoreRepository componentScoreRepository;

    @Override
    public List<CourseComponentResponseDTO> getByCourseId(Long courseId) {
        return courseComponentMapper.toDto(courseComponentRepository.findByCourseId(courseId));
    }

    @Override
    public CourseComponentResponseDTO getById(Long id) {
        CourseComponent component = courseComponentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Thành phần điểm không tồn tại với id: " + id));
        return courseComponentMapper.toDto(component);
    }

    /**
     * TỐI ƯU:
     * TRƯỚC: findById(course) + stream().sum() trên tất cả components + save + loop saveAll(scores) = 4+ queries
     * SAU: sumWeightByCourseId (1 native SUM) + save + bulkInsertScoresForNewComponent (1 native INSERT) = 3 queries
     */
    @Override
    @Transactional
    public CourseComponentResponseDTO create(Long courseId, CourseComponentRequestDTO request) {
        // 1 native SUM query thay vì load toàn bộ components rồi stream().sum()
        double currentWeight = courseComponentRepository.sumWeightByCourseId(courseId);
        if (currentWeight + request.getWeight() > 1.001) {
            throw new IllegalArgumentException("Tổng trọng số vượt quá 100%");
        }

        // getReferenceById: 0 queries
        CourseComponent component = courseComponentMapper.toEntity(request);
        component.setCourse(courseRepository.getReferenceById(courseId));
        CourseComponent saved = courseComponentRepository.save(component);

        // 1 native INSERT...SELECT tạo score slots cho TẤT CẢ enrollments
        // thay vì load enrollments + loop save
        componentScoreRepository.bulkInsertScoresForNewComponent(saved.getId(), courseId);

        return courseComponentMapper.toDto(saved);
    }

    @Override
    @Transactional
    public CourseComponentResponseDTO update(Long id, CourseComponentRequestDTO request) {
        CourseComponent existing = courseComponentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Thành phần điểm không tồn tại với id: " + id));
        courseComponentMapper.updateEntity(request, existing);
        CourseComponent updated = courseComponentRepository.save(existing);
        return courseComponentMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        CourseComponent component = courseComponentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Thành phần điểm không tồn tại với id: " + id));
        // 1 bulk DELETE thay vì cascade individual deletes
        componentScoreRepository.deleteAllByCourseComponentId(id);
        courseComponentRepository.delete(component);
    }
}
