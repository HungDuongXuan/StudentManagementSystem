package com.studentmanagement.dto.request.search;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StudentSearchRequestDTO {
    private String name;
    private String email;
    private String studentCode;
    private List<Long> classroomIds;
    private String status;
    private int page = 0;
    private int size = 10;
}
