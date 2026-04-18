package com.studentmanagement.dto.response;

import com.studentmanagement.entity.type.OperationalStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MajorResponseDTO {
    private Long id;
    private String name;
    private String majorCode;
    private OperationalStatus status;
    private LocalDateTime createdAt;
}
