package com.studentmanagement.dto.response;

import com.studentmanagement.entity.type.OperationalStatus;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClassroomResponseDTO {
    private Long id;
    private String name;
    private Long curriculumId;
    private String curriculumName;
    private OperationalStatus status;
    private int studentCount;
}
