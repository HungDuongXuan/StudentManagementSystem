package com.studentmanagement.controller;

import com.studentmanagement.dto.request.MajorRequestDTO;
import com.studentmanagement.dto.request.search.MajorSearchRequestDTO;
import com.studentmanagement.dto.response.MajorResponseDTO;
import com.studentmanagement.service.IMajorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/majors")
@RequiredArgsConstructor
public class MajorController {
    private final IMajorService majorService;

    @PostMapping
    public ResponseEntity<MajorResponseDTO> create(@Valid @RequestBody MajorRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(majorService.createMajor(request));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<MajorResponseDTO>> createBatch(@Valid @RequestBody List<MajorRequestDTO> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(majorService.createMajorsBatch(requests));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<MajorResponseDTO>> search(@RequestBody MajorSearchRequestDTO request) {
        return ResponseEntity.ok(majorService.searchMajors(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MajorResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(majorService.getMajorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MajorResponseDTO> update(@PathVariable Long id, @Valid @RequestBody MajorRequestDTO request) {
        return ResponseEntity.ok(majorService.updateMajor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        majorService.deleteMajor(id);
        return ResponseEntity.noContent().build();
    }
}
