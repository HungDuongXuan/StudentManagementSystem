package com.studentmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CourseRequestDTO {
    @NotBlank(message = "Tên môn học không được để trống")
    private String name;

    @NotNull(message = "Sức chứa không được để trống")
    @Min(value = 10, message = "Sức chứa tối thiểu 10 sinh viên")
    private Integer studentCapacity;

    @NotNull(message = "Faculty ID không được để trống")
    private Long facultyId;
}
