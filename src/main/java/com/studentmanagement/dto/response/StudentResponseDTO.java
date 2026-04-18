package com.studentmanagement.dto.response;

import com.studentmanagement.entity.type.Gender;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentResponseDTO {
    private Long id;
    private String name;
    private String studentCode;
    private String email;
    private LocalDate dob;
    private Gender gender;
    private String address;
    private String classroomName;
    private List<Long> classroomIds;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
