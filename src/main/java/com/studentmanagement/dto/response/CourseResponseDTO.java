package com.studentmanagement.dto.response;

import com.studentmanagement.entity.type.OperationalStatus;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CourseResponseDTO {
    private Long id;
    private String name;
    private Integer studentCapacity;
    private Long facultyId;
    private String facultyName;
    private OperationalStatus status;
}
