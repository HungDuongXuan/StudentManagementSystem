package com.studentmanagement.controller;

import com.studentmanagement.dto.request.CourseComponentRequestDTO;
import com.studentmanagement.dto.response.CourseComponentResponseDTO;
import com.studentmanagement.service.ICourseComponentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/components")
@RequiredArgsConstructor
public class CourseComponentController {
    private final ICourseComponentService courseComponentService;

    @GetMapping
    public ResponseEntity<List<CourseComponentResponseDTO>> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseComponentService.getByCourseId(courseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseComponentResponseDTO> getById(@PathVariable Long courseId, @PathVariable Long id) {
        return ResponseEntity.ok(courseComponentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CourseComponentResponseDTO> create(
            @PathVariable Long courseId,
            @Valid @RequestBody CourseComponentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseComponentService.create(courseId, request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseComponentResponseDTO> update(
            @PathVariable Long courseId,
            @PathVariable Long id,
            @Valid @RequestBody CourseComponentRequestDTO request) {
        return ResponseEntity.ok(courseComponentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long courseId, @PathVariable Long id) {
        courseComponentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
