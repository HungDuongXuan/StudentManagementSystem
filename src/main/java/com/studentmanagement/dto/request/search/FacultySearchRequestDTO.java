package com.studentmanagement.dto.request.search;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class FacultySearchRequestDTO {
    private String name;
    private Long campusId;
    private int page = 0;
    private int size = 10;
}
