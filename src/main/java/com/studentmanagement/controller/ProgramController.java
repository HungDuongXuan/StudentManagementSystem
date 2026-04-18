package com.studentmanagement.controller;

import com.studentmanagement.dto.request.ProgramRequestDTO;
import com.studentmanagement.dto.request.search.ProgramSearchRequestDTO;
import com.studentmanagement.dto.response.ProgramResponseDTO;
import com.studentmanagement.service.IProgramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
@RequiredArgsConstructor
public class ProgramController {
    private final IProgramService programService;

    @PostMapping
    public ResponseEntity<ProgramResponseDTO> create(@Valid @RequestBody ProgramRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(programService.createProgram(request));
    }

    @PostMapping("/batch")
    public ResponseEntity<List<ProgramResponseDTO>> createBatch(@Valid @RequestBody List<ProgramRequestDTO> requests) {
        return ResponseEntity.status(HttpStatus.CREATED).body(programService.createProgramsBatch(requests));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ProgramResponseDTO>> search(@RequestBody ProgramSearchRequestDTO request) {
        return ResponseEntity.ok(programService.searchPrograms(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(programService.getProgramById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProgramRequestDTO request) {
        return ResponseEntity.ok(programService.updateProgram(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        programService.deleteProgram(id);
        return ResponseEntity.noContent().build();
    }
}
