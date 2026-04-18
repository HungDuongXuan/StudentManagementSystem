package com.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.studentmanagement.entity.type.OperationalStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

import static com.studentmanagement.validation.ValidationConstants.PROGRAM_CODE_REGEX;

@Entity
@Table(name = "programs")
@SQLDelete(sql = "UPDATE student_management_test.programs SET status = 'DELETED' WHERE id = ?")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "program_seq")
    @SequenceGenerator(name = "program_seq", sequenceName = "program_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToMany(mappedBy = "programs")
    private List<Campus> campuses;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Curriculum> curriculums;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = PROGRAM_CODE_REGEX, message = "Program code must be 2 uppercase letters")
    private String programCode;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OperationalStatus status = OperationalStatus.ACTIVE;
}
