package com.studentmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import static com.studentmanagement.validation.ValidationConstants.MAJOR_CODE_REGEX;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MajorRequestDTO {
    @NotBlank(message = "Tên ngành không được để trống")
    private String name;

    @NotBlank(message = "Mã ngành không được để trống")
    @Pattern(regexp = MAJOR_CODE_REGEX, message = "Mã ngành phải là 2 chữ cái in hoa")
    private String majorCode;
    private String status;
}