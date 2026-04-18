package com.studentmanagement.mapper;

import com.studentmanagement.dto.response.EnrollmentResponseDTO;
import com.studentmanagement.dto.response.ComponentScoreResponseDTO;
import com.studentmanagement.entity.Enrollment;
import com.studentmanagement.entity.ComponentScore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EnrollmentMapper {

    public EnrollmentResponseDTO toDto(Enrollment entity) {
        if (entity == null) return null;

        List<ComponentScoreResponseDTO> scores = entity.getComponentScores() != null
                ? entity.getComponentScores().stream().map(this::toScoreDto).toList()
                : List.of();

        return EnrollmentResponseDTO.builder()
                .id(entity.getId())
                .studentId(entity.getStudent() != null ? entity.getStudent().getId() : null)
                .studentName(entity.getStudent() != null ? entity.getStudent().getName() : null)
                .courseId(entity.getCourse() != null ? entity.getCourse().getId() : null)
                .courseName(entity.getCourse() != null ? entity.getCourse().getName() : null)
                .finalScore(entity.getFinalScore())
                .componentScores(scores)
                .build();
    }

    public List<EnrollmentResponseDTO> toDto(List<Enrollment> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toDto).toList();
    }

    private ComponentScoreResponseDTO toScoreDto(ComponentScore cs) {
        if (cs == null) return null;
        return ComponentScoreResponseDTO.builder()
                .id(cs.getId())
                .courseComponentId(cs.getCourseComponent() != null ? cs.getCourseComponent().getId() : null)
                .componentName(cs.getCourseComponent() != null ? cs.getCourseComponent().getName() : null)
                .weight(cs.getCourseComponent() != null ? cs.getCourseComponent().getWeight() : null)
                .score(cs.getScore())
                .build();
    }
}
