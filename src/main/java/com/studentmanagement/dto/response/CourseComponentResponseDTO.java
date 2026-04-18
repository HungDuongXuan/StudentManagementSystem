package com.studentmanagement.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CourseComponentResponseDTO {
    private Long id;
    private String name;
    private Double weight;
    private Long courseId;
    private String courseName;
}
