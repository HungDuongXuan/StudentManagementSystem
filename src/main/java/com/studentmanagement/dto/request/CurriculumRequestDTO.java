package com.studentmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CurriculumRequestDTO {
    @NotBlank(message = "Tên chương trình đào tạo không được để trống")
    private String name;

    @NotNull(message = "Program ID không được để trống")
    private Long programId;

    @NotNull(message = "Major ID không được để trống")
    private Long majorId;

    @NotNull(message = "Faculty ID không được để trống")
    private Long facultyId;
}
