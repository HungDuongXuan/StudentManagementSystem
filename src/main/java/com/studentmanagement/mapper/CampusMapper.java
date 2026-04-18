package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.CampusRequestDTO;
import com.studentmanagement.dto.response.CampusResponseDTO;
import com.studentmanagement.entity.Campus;
import com.studentmanagement.entity.type.OperationalStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CampusMapper {

    public CampusResponseDTO toDto(Campus entity) {
        if (entity == null) return null;
        return CampusResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .campusCode(entity.getCampusCode())
                .location(entity.getLocation())
                .tel(entity.getTel())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public List<CampusResponseDTO> toDto(List<Campus> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toDto).toList();
    }

    public Campus toEntity(CampusRequestDTO dto) {
        if (dto == null) return null;
        return Campus.builder()
                .name(dto.getName())
                .campusCode(dto.getCampusCode())
                .location(dto.getLocation())
                .tel(dto.getTel())
                .build();
    }

    public void updateEntity(CampusRequestDTO dto, Campus entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
        entity.setCampusCode(dto.getCampusCode());
        entity.setLocation(dto.getLocation());
        entity.setTel(dto.getTel());
    
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            try {
                entity.setStatus(OperationalStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Ignore invalid status
            }
        }
    }
}
