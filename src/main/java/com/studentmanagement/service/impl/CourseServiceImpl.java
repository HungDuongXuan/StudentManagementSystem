package com.studentmanagement.service.impl;

import com.studentmanagement.dto.request.CourseRequestDTO;
import com.studentmanagement.dto.request.search.CourseSearchRequestDTO;
import com.studentmanagement.dto.response.CourseResponseDTO;
import com.studentmanagement.entity.Course;
import com.studentmanagement.entity.type.OperationalStatus;
import com.studentmanagement.mapper.CourseMapper;
import com.studentmanagement.repository.CourseRepository;
import com.studentmanagement.repository.FacultyRepository;
import com.studentmanagement.service.ICourseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements ICourseService {
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final FacultyRepository facultyRepository;

    @Override
    @Transactional
    public CourseResponseDTO createCourse(CourseRequestDTO request) {
        Course course = courseMapper.toEntity(request);
        // getReferenceById: 0 queries thay vì findById(faculty) = 1 query
        course.setFaculty(facultyRepository.getReferenceById(request.getFacultyId()));
        course.setStatus(OperationalStatus.ACTIVE);
        Course saved = courseRepository.save(course);
        return courseMapper.toDto(saved);
    }

    @Override
    public List<CourseResponseDTO> getAllCourses() {
        return courseMapper.toDto(courseRepository.findAll());
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Môn học không tồn tại với id: " + id));
        return courseMapper.toDto(course);
    }

    @Override
    @Transactional
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO request) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Môn học không tồn tại với id: " + id));

        courseMapper.updateEntity(request, existing);
        // getReferenceById: 0 queries
        existing.setFaculty(facultyRepository.getReferenceById(request.getFacultyId()));
        Course updated = courseRepository.save(existing);
        return courseMapper.toDto(updated);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new IllegalArgumentException("Môn học không tồn tại với id: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseResponseDTO> getCoursesByFacultyId(Long facultyId) {
        return courseMapper.toDto(courseRepository.findByFacultyId(facultyId));
    }

    @Override
    public Page<CourseResponseDTO> searchCourses(CourseSearchRequestDTO request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<Course> page = courseRepository.searchCourses(request.getName(), request.getFacultyId(), pageable);
        return page.map(courseMapper::toDto);
    }

    @Override
    @Transactional
    public List<CourseResponseDTO> createCoursesBatch(List<CourseRequestDTO> requests) {
        List<Course> courses = requests.stream().map(dto -> {
            Course course = courseMapper.toEntity(dto);
            course.setFaculty(facultyRepository.getReferenceById(dto.getFacultyId()));
            course.setStatus(OperationalStatus.ACTIVE);
            return course;
        }).toList();
        // batch INSERT nhờ batch_size=30 + order_inserts
        return courseRepository.saveAll(courses).stream().map(courseMapper::toDto).toList();
    }
}
