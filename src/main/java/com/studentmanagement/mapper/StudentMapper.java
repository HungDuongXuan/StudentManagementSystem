package com.studentmanagement.mapper;

import com.studentmanagement.dto.request.StudentRequestDTO;
import com.studentmanagement.dto.response.StudentResponseDTO;
import com.studentmanagement.entity.Classroom;
import com.studentmanagement.entity.Student;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    public StudentResponseDTO toDto(Student entity) {
        if (entity == null) return null;

        Set<Classroom> classrooms = entity.getClassrooms();
        String classroomName = (classrooms != null && !classrooms.isEmpty())
                ? classrooms.stream().map(Classroom::getName).collect(Collectors.joining(", "))
                : "";
        List<Long> classroomIds = (classrooms != null && !classrooms.isEmpty())
                ? classrooms.stream().map(Classroom::getId).toList()
                : Collections.emptyList();

        return StudentResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .studentCode(entity.getStudentCode())
                .email(entity.getEmail())
                .dob(entity.getDob())
                .gender(entity.getGender())
                .address(entity.getAddress())
                .classroomName(classroomName)
                .classroomIds(classroomIds)
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public List<StudentResponseDTO> toDto(List<Student> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toDto).toList();
    }

    public Student toEntity(StudentRequestDTO dto) {
        if (dto == null) return null;
        return Student.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .dob(dto.getDob())
                .gender(dto.getGender())
                .address(dto.getAddress())
                .build();
    }

    public void updateEntity(StudentRequestDTO dto, Student entity) {
        if (dto == null || entity == null) return;
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setDob(dto.getDob());
        entity.setGender(dto.getGender());
        entity.setAddress(dto.getAddress());
    }
}
