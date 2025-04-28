package org.mentorbridge.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mentorbridge.service.StudentService;
import org.mentorbridge.dto.StudentDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/add")
    public StudentDTO add(@RequestBody StudentDTO studentDTO) {
        return studentService.add(studentDTO);
    }

    @GetMapping("/{id}")
    public StudentDTO get(@PathVariable String id) {
        return studentService.get(id);
    }

    @GetMapping
    public List<StudentDTO> getAll() {
        return studentService.getAll();
    }

    @PutMapping("/{id}")
    public StudentDTO update(@PathVariable String id, @RequestBody StudentDTO studentDTO) {
        return studentService.update(id, studentDTO);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id) {
        studentService.delete(id);
        return "Student with id " + id + " has been deleted";
    }
}
