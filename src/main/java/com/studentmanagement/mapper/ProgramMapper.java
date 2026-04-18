package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.ProgramRequestDTO;
import com.studentmanagement.dto.response.CampusResponseDTO;
import com.studentmanagement.dto.response.ProgramResponseDTO;
import com.studentmanagement.entity.Campus;
import com.studentmanagement.entity.type.OperationalStatus;
import com.studentmanagement.entity.Program;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProgramMapper {

    private final CampusMapper campusMapper;

    public ProgramResponseDTO toDto(Program entity) {
        if (entity == null) return null;

        List<CampusResponseDTO> campusDtos = entity.getCampuses() != null
                ? campusMapper.toDto(entity.getCampuses())
                : List.of();

        return ProgramResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .programCode(entity.getProgramCode())
                .campusResponseDTOS(campusDtos)
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public List<ProgramResponseDTO> toDto(List<Program> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toDto).toList();
    }

    public Program toEntity(ProgramRequestDTO dto) {
        if (dto == null) return null;
        return Program.builder()
                .name(dto.getName())
                .programCode(dto.getProgramCode())
                .build();
    }

    public void updateEntity(ProgramRequestDTO dto, Program entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
        entity.setProgramCode(dto.getProgramCode());
    
        if (dto.getStatus() != null && !dto.getStatus().isBlank()) {
            try {
                entity.setStatus(OperationalStatus.valueOf(dto.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                // Ignore invalid status
            }
        }
    }
}
