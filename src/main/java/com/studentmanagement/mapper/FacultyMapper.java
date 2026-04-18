package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.FacultyRequestDTO;
import com.studentmanagement.dto.response.FacultyResponseDTO;
import com.studentmanagement.entity.Faculty;
import com.studentmanagement.entity.type.OperationalStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FacultyMapper {

    public FacultyResponseDTO toDto(Faculty entity) {
        if (entity == null) return null;
        return FacultyResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .campusId(entity.getCampus() != null ? entity.getCampus().getId() : null)
                .campusName(entity.getCampus() != null ? entity.getCampus().getName() : null)
                .status(entity.getStatus())
                .build();
    }

    public List<FacultyResponseDTO> toDto(List<Faculty> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toDto).toList();
    }

    public Faculty toEntity(FacultyRequestDTO dto) {
        if (dto == null) return null;
        return Faculty.builder()
                .name(dto.getName())
                .build();
    }

    public void updateEntity(FacultyRequestDTO dto, Faculty entity) {
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
