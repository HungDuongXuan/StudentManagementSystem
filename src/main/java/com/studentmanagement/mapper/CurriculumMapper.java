package com.studentmanagement.mapper;

import com.studentmanagement.dto.response.CurriculumResponseDTO;
import com.studentmanagement.dto.request.CurriculumRequestDTO;
import com.studentmanagement.entity.Curriculum;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurriculumMapper {

    public CurriculumResponseDTO toDto(Curriculum entity) {
        if (entity == null) return null;
        return CurriculumResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .programId(entity.getProgram() != null ? entity.getProgram().getId() : null)
                .programName(entity.getProgram() != null ? entity.getProgram().getName() : null)
                .majorId(entity.getMajor() != null ? entity.getMajor().getId() : null)
                .majorName(entity.getMajor() != null ? entity.getMajor().getName() : null)
                .facultyId(entity.getFaculty() != null ? entity.getFaculty().getId() : null)
                .facultyName(entity.getFaculty() != null ? entity.getFaculty().getName() : null)
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public List<CurriculumResponseDTO> toDto(List<Curriculum> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toDto).toList();
    }

    public Curriculum toEntity(CurriculumRequestDTO dto) {
        if (dto == null) return null;
        return Curriculum.builder()
                .name(dto.getName())
                .build();
    }

    public void updateEntity(CurriculumRequestDTO dto, Curriculum entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
    }
}
