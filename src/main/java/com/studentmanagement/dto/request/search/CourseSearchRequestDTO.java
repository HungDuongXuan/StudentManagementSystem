package com.studentmanagement.dto.request.search;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseSearchRequestDTO {
    private String name;
    private Long facultyId;
    private int page = 0;
    private int size = 10;
}
