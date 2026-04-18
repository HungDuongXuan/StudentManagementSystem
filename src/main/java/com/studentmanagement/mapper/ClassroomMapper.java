package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.ClassroomRequestDTO;
import com.studentmanagement.dto.response.ClassroomResponseDTO;
import com.studentmanagement.entity.Classroom;
import com.studentmanagement.entity.type.OperationalStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClassroomMapper {

    public ClassroomResponseDTO toDto(Classroom entity) {
        if (entity == null) return null;
        return ClassroomResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .curriculumId(entity.getCurriculum() != null ? entity.getCurriculum().getId() : null)
                .curriculumName(entity.getCurriculum() != null ? entity.getCurriculum().getName() : null)
                .status(entity.getStatus())
                .studentCount(entity.getStudents() != null ? entity.getStudents().size() : 0)
                .build();
    }

    public List<ClassroomResponseDTO> toDto(List<Classroom> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toDto).toList();
    }

    public Classroom toEntity(ClassroomRequestDTO dto) {
        if (dto == null) return null;
        return Classroom.builder()
                .name(dto.getName())
                .build();
    }

    public void updateEntity(ClassroomRequestDTO dto, Classroom entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
    
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            try {
                entity.setStatus(OperationalStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Ignore invalid status
            }
        }
    }
}
