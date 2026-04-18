package com.studentmanagement.dto.response;

import com.studentmanagement.entity.type.OperationalStatus;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProgramResponseDTO {
    private Long id;
    private String name;
    private String programCode;
    private List<CampusResponseDTO> campusResponseDTOS;
    private OperationalStatus status;
    private LocalDateTime createdAt;
}