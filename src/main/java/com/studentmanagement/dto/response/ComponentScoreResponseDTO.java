package com.studentmanagement.dto.response;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ComponentScoreResponseDTO {
    private Long id;
    private Long courseComponentId;
    private String componentName;
    private Double weight;
    private Double score;
}
