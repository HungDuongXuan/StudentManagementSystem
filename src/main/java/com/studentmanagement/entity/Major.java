package com.studentmanagement.entity;

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

import static com.studentmanagement.validation.ValidationConstants.MAJOR_CODE_REGEX;

@Entity
@Table(name = "majors")
@SQLDelete(sql = "UPDATE student_management_test.majors SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status != 'DELETED'")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "major_seq")
    @SequenceGenerator(name = "major_seq", sequenceName = "major_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "major", cascade = CascadeType.ALL)
    private List<Curriculum> curriculums;

    @Column(nullable = false, unique = true)
    @Pattern(regexp = MAJOR_CODE_REGEX, message = "Major code must be 2 uppercase letters")
    private String majorCode;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OperationalStatus status = OperationalStatus.ACTIVE;
}
