package com.studentmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FacultyRequestDTO {
    @NotBlank(message = "Tên khoa không được để trống")
    private String name;

    @NotNull(message = "Campus ID không được để trống")
    private Long campusId;
    private String status;
}
