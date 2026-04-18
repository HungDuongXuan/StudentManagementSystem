package com.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "component_scores", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"enrollment_id", "course_component_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComponentScore {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "component_score_seq")
    @SequenceGenerator(name = "component_score_seq", sequenceName = "component_score_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    @JsonBackReference
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "course_component_id", nullable = false)
    private CourseComponent courseComponent;

    private Double score;
}
