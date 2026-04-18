package com.studentmanagement.mapper;

import com.studentmanagement.dto.response.ComponentScoreResponseDTO;
import com.studentmanagement.entity.ComponentScore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComponentScoreMapper {

    public ComponentScoreResponseDTO toDto(ComponentScore entity) {
        if (entity == null) return null;
        return ComponentScoreResponseDTO.builder()
                .id(entity.getId())
                .courseComponentId(entity.getCourseComponent() != null ? entity.getCourseComponent().getId() : null)
                .componentName(entity.getCourseComponent() != null ? entity.getCourseComponent().getName() : null)
                .weight(entity.getCourseComponent() != null ? entity.getCourseComponent().getWeight() : null)
                .score(entity.getScore())
                .build();
    }

    public List<ComponentScoreResponseDTO> toDto(List<ComponentScore> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toDto).toList();
    }
}
