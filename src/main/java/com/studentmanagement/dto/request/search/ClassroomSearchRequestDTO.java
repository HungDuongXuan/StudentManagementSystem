package com.studentmanagement.dto.request.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomSearchRequestDTO {
    private String name;
    private Long curriculumId;
    private int page = 0;
    private int size = 10;
}
