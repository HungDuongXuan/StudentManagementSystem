package com.studentmanagement.dto.request.search;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProgramSearchRequestDTO {
    private String name;
    private String programCode;
    private Long campusId;
    private int page = 0;
    private int size = 10;
}
