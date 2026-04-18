package com.studentmanagement.controller;

import com.studentmanagement.dto.request.CourseRequestDTO;
import com.studentmanagement.dto.request.search.CourseSearchRequestDTO;
import com.studentmanagement.dto.response.CourseResponseDTO;
import com.studentmanagement.service.ICourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final ICourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponseDTO> create(@Valid @RequestBody CourseRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(request));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAll() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @PostMapping("/search")
    public ResponseEntity<Page<CourseResponseDTO>> search(@RequestBody CourseSearchRequestDTO request) {
        return ResponseEntity.ok(courseService.searchCourses(request));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CourseResponseDTO>> createBatch(@Valid @RequestBody List<CourseRequestDTO> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCoursesBatch(requests));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CourseRequestDTO request) {
        return ResponseEntity.ok(courseService.updateCourse(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<CourseResponseDTO>> getByFaculty(@PathVariable Long facultyId) {
        return ResponseEntity.ok(courseService.getCoursesByFacultyId(facultyId));
    }
}
