package com.studentmanagement.dto.response;

import com.studentmanagement.entity.type.OperationalStatus;
import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CampusResponseDTO {
    private Long id;
    private String name;
    private String campusCode;
    private String location;
    private String tel;
    private OperationalStatus status;
    private LocalDateTime createdAt;
}
