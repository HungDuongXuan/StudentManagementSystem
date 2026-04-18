package com.studentmanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.studentmanagement.entity.type.OperationalStatus;
import com.studentmanagement.validation.ValidationConstants;
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

@Entity
@Table(name = "campuses")
@SQLDelete(sql = "UPDATE student_management_test.campuses SET status = 'DELETED' WHERE id = ?")
@Where(clause = "status != 'DELETED'")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Campus {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "campus_seq")
    @SequenceGenerator(name = "campus_seq", sequenceName = "campus_seq", allocationSize = 1)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    @Pattern(regexp = ValidationConstants.PHONE_NUMBER_REGEX, message = "Invalid phone number format")
    private String tel;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = ValidationConstants.CAMPUS_CODE_REGEX, message = "Invalid campus code format")
    private String campusCode;

    @OneToMany(mappedBy = "campus", cascade = CascadeType.ALL)
    private List<Faculty> faculties;

    @ManyToMany
    @JoinTable(
            name = "campus_program",
            joinColumns = @JoinColumn(name = "campus_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id")
    )
    private List<Program> programs;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column()
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private OperationalStatus status = OperationalStatus.ACTIVE;
}
