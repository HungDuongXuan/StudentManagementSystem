package com.studentmanagement.controller;

import com.studentmanagement.dto.request.CampusRequestDTO;
import com.studentmanagement.dto.response.CampusResponseDTO;
import com.studentmanagement.service.ICampusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campuses")
@RequiredArgsConstructor
public class CampusController {
    private final ICampusService campusService;

    @PostMapping
    public ResponseEntity<CampusResponseDTO> create(@Valid @RequestBody CampusRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campusService.createCampus(request));
    }

    @GetMapping
    public ResponseEntity<List<CampusResponseDTO>> getAll() {
        return ResponseEntity.ok(campusService.getAllCampuses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampusResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(campusService.getCampusById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampusResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CampusRequestDTO request) {
        return ResponseEntity.ok(campusService.updateCampus(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        campusService.deleteCampus(id);
        return ResponseEntity.noContent().build();
    }
}
