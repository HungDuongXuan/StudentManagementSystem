package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.MajorRequestDTO;
import com.studentmanagement.dto.response.MajorResponseDTO;
import com.studentmanagement.entity.Major;
import com.studentmanagement.entity.type.OperationalStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MajorMapper {

    public MajorResponseDTO toDto(Major entity) {
        if (entity == null) return null;
        return MajorResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .majorCode(entity.getMajorCode())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public List<MajorResponseDTO> toDto(List<Major> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toDto).toList();
    }

    public Major toEntity(MajorRequestDTO dto) {
        if (dto == null) return null;
        return Major.builder()
                .name(dto.getName())
                .majorCode(dto.getMajorCode())
                .build();
    }

    public void updateEntity(MajorRequestDTO dto, Major entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
        entity.setMajorCode(dto.getMajorCode());
    
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            try {
                entity.setStatus(OperationalStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Ignore invalid status
            }
        }
    }
}
