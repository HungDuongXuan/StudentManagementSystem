package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.CourseRequestDTO;
import com.studentmanagement.dto.response.CourseResponseDTO;
import com.studentmanagement.entity.Course;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CourseMapper {

    public CourseResponseDTO toDto(Course entity) {
        if (entity == null) return null;
        return CourseResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .studentCapacity(entity.getStudentCapacity())
                .facultyId(entity.getFaculty() != null ? entity.getFaculty().getId() : null)
                .facultyName(entity.getFaculty() != null ? entity.getFaculty().getName() : null)
                .status(entity.getStatus())
                .build();
    }

    public List<CourseResponseDTO> toDto(List<Course> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toDto).toList();
    }

    public Course toEntity(CourseRequestDTO dto) {
        if (dto == null) return null;
        return Course.builder()
                .name(dto.getName())
                .studentCapacity(dto.getStudentCapacity())
                .build();
    }

    public void updateEntity(CourseRequestDTO dto, Course entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
        entity.setStudentCapacity(dto.getStudentCapacity());
    }
}
