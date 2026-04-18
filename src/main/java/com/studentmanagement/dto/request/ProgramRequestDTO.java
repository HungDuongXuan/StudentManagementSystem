package com.studentmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

import static com.studentmanagement.validation.ValidationConstants.PROGRAM_CODE_REGEX;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProgramRequestDTO {
    @NotBlank(message = "Tên chương trình không được để trống")
    private String name;

    @NotBlank(message = "Mã chương trình không được để trống")
    @Pattern(regexp = PROGRAM_CODE_REGEX, message = "Mã chương trình phải là 2 chữ cái in hoa")
    private String programCode;

    private List<Long> campusIds;

    private String status;
}
