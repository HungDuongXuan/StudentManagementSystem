package com.studentmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClassroomRequestDTO {
    @NotBlank(message = "Tên lớp không được để trống")
    private String name;

    @NotNull(message = "Curriculum ID không được để trống")
    private Long curriculumId;
    private String status;
}