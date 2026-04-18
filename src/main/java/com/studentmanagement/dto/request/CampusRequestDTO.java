package com.studentmanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import static com.studentmanagement.validation.ValidationConstants.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CampusRequestDTO {
    @NotBlank(message = "Tên campus không được để trống")
    @Size(max = 100, message = "Tên campus tối đa 100 ký tự")
    private String name;

    @NotBlank(message = "Mã campus không được để trống")
    @Pattern(regexp = CAMPUS_CODE_REGEX, message = "Mã campus phải là 1 chữ cái in hoa")
    private String campusCode;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String location;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = PHONE_NUMBER_REGEX, message = "Số điện thoại không hợp lệ")
    private String tel;
    
    private String status;
}
