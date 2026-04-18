package com.studentmanagement.controller;

import com.studentmanagement.dto.request.StudentRequestDTO;
import com.studentmanagement.dto.request.search.StudentSearchRequestDTO;
import com.studentmanagement.dto.response.StudentResponseDTO;
import com.studentmanagement.service.IStudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final IStudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponseDTO> create(@Valid @RequestBody StudentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(request));
    }

    @PostMapping("/search")
    public ResponseEntity<Page<StudentResponseDTO>> search(@RequestBody StudentSearchRequestDTO request) {
        return ResponseEntity.ok(studentService.searchStudents(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> update(@PathVariable Long id, @Valid @RequestBody StudentRequestDTO request) {
        return ResponseEntity.ok(studentService.updateStudent(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/add-classroom")
    public ResponseEntity<StudentResponseDTO> addClassroom(@PathVariable Long id, @RequestParam Long classroomId) {
        return ResponseEntity.ok(studentService.addClassroomToStudent(id, classroomId));
    }
}
