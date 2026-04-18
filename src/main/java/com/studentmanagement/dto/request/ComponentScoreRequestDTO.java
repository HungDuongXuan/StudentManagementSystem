package com.studentmanagement.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ComponentScoreRequestDTO {
    @NotNull(message = "Course component ID không được để trống")
    private Long courseComponentId;

    @DecimalMin(value = "0.0", message = "Điểm tối thiểu là 0")
    @DecimalMax(value = "10.0", message = "Điểm tối đa là 10")
    private Double score;
}
