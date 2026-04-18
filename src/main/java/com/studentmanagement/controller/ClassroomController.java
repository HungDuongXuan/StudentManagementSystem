package com.studentmanagement.controller;

import com.studentmanagement.dto.request.ClassroomRequestDTO;
import com.studentmanagement.dto.request.search.ClassroomSearchRequestDTO;
import com.studentmanagement.dto.response.ClassroomResponseDTO;
import com.studentmanagement.dto.response.StudentResponseDTO;
import com.studentmanagement.service.IClassroomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
public class ClassroomController {
    private final IClassroomService classroomService;

    @PostMapping
    public ResponseEntity<ClassroomResponseDTO> create(@Valid @RequestBody ClassroomRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(classroomService.createClassroom(request));
    }

    @GetMapping
    public ResponseEntity<List<ClassroomResponseDTO>> getAll() {
        return ResponseEntity.ok(classroomService.getAllClassrooms());
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ClassroomResponseDTO>> search(@RequestBody ClassroomSearchRequestDTO request) {
        return ResponseEntity.ok(classroomService.searchClassrooms(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ClassroomRequestDTO request) {
        return ResponseEntity.ok(classroomService.updateClassroom(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classroomService.deleteClassroom(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<StudentResponseDTO>> getStudents(@PathVariable Long id) {
        return ResponseEntity.ok(classroomService.getStudentsByClassroom(id));
    }
}
