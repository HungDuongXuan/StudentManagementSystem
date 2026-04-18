package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.CourseComponentRequestDTO;
import com.studentmanagement.dto.response.CourseComponentResponseDTO;
import com.studentmanagement.entity.CourseComponent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseComponentMapper {

    public CourseComponentResponseDTO toDto(CourseComponent entity) {
        if (entity == null) return null;
        return CourseComponentResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .weight(entity.getWeight())
                .courseId(entity.getCourse() != null ? entity.getCourse().getId() : null)
                .courseName(entity.getCourse() != null ? entity.getCourse().getName() : null)
                .build();
    }

    public List<CourseComponentResponseDTO> toDto(List<CourseComponent> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toDto).toList();
    }

    public CourseComponent toEntity(CourseComponentRequestDTO dto) {
        if (dto == null) return null;
        return CourseComponent.builder()
                .name(dto.getName())
                .weight(dto.getWeight())
                .build();
    }

    public void updateEntity(CourseComponentRequestDTO dto, CourseComponent entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
        entity.setWeight(dto.getWeight());
    }
}
