package com.studentmanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EnrollmentRequestDTO {
    @NotNull(message = "Student ID không được để trống")
    private Long studentId;

    @NotNull(message = "Course ID không được để trống")
    private Long courseId;
}
