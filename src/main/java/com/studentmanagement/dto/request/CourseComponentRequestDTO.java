package com.studentmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CourseComponentRequestDTO {
    @NotBlank(message = "Tên thành phần điểm không được để trống")
    private String name;

    @NotNull(message = "Trọng số không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Trọng số phải lớn hơn 0")
    @DecimalMax(value = "1.0", message = "Trọng số tối đa là 1.0")
    private Double weight;
}
