package com.studentmanagement.controller;

import com.studentmanagement.dto.request.CurriculumRequestDTO;
import com.studentmanagement.dto.request.search.CurriculumSearchRequestDTO;
import com.studentmanagement.dto.response.CurriculumResponseDTO;
import com.studentmanagement.service.ICurriculumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/curriculums")
@RequiredArgsConstructor
public class CurriculumController {
    private final ICurriculumService curriculumService;

    @PostMapping
    public ResponseEntity<CurriculumResponseDTO> create(@Valid @RequestBody CurriculumRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(curriculumService.createCurriculum(request));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CurriculumResponseDTO>> createBatch(@Valid @RequestBody List<CurriculumRequestDTO> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(curriculumService.createCurriculumsBatch(requests));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<CurriculumResponseDTO>> search(@RequestBody CurriculumSearchRequestDTO request) {
        return ResponseEntity.ok(curriculumService.searchCurriculums(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurriculumResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(curriculumService.getCurriculumById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CurriculumResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CurriculumRequestDTO request) {
        return ResponseEntity.ok(curriculumService.updateCurriculum(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        curriculumService.deleteCurriculum(id);
        return ResponseEntity.noContent().build();
    }
}
