package com.studentmanagement.dto.response;

import com.studentmanagement.entity.type.OperationalStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CurriculumResponseDTO {
    private Long id;
    private String name;
    private Long programId;
    private String programName;
    private Long majorId;
    private String majorName;
    private Long facultyId;
    private String facultyName;
    private OperationalStatus status;
    private LocalDateTime createdAt;
}
