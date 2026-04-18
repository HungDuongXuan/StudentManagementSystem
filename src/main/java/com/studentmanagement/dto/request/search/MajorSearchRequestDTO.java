package com.studentmanagement.dto.request.search;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MajorSearchRequestDTO {
    private String name;
    private String majorCode;
    private int page = 0;
    private int size = 10;
}
