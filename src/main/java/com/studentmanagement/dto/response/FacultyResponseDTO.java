package com.studentmanagement.dto.response;

import com.studentmanagement.entity.type.OperationalStatus;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FacultyResponseDTO {
    private Long id;
    private String name;
    private Long campusId;
    private String campusName;
    private OperationalStatus status;
}
