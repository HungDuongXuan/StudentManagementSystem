package com.studentmanagement.controller;

import com.studentmanagement.dto.request.FacultyRequestDTO;
import com.studentmanagement.dto.request.search.FacultySearchRequestDTO;
import com.studentmanagement.dto.response.FacultyResponseDTO;
import com.studentmanagement.service.IFacultyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculties")
@RequiredArgsConstructor
public class FacultyController {
    private final IFacultyService facultyService;

    @PostMapping
    public ResponseEntity<FacultyResponseDTO> create(@Valid @RequestBody FacultyRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyService.createFaculty(request));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<FacultyResponseDTO>> createBatch(@Valid @RequestBody List<FacultyRequestDTO> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyService.createFacultiesBatch(requests));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<FacultyResponseDTO>> search(@RequestBody FacultySearchRequestDTO request) {
        return ResponseEntity.ok(facultyService.searchFaculties(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getFacultyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FacultyResponseDTO> update(@PathVariable Long id, @Valid @RequestBody FacultyRequestDTO request) {
        return ResponseEntity.ok(facultyService.updateFaculty(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.noContent().build();
    }
}
