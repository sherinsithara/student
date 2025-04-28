package org.mentorbridge.service;

import org.mentorbridge.dto.StudentDTO;
import org.mentorbridge.entity.StudentEntity;
import org.mentorbridge.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public StudentDTO add(StudentDTO studentDTO) {
        StudentEntity studentEntity = StudentEntity.builder()
                .id(studentDTO.getId())
                .firstName(studentDTO.getFirstName())
                .lastName(studentDTO.getLastName())
                .email(studentDTO.getEmail())
                .build();

        StudentEntity savedStudent = studentRepository.save(studentEntity);

        return StudentDTO.builder()
                .id(savedStudent.getId())
                .firstName(savedStudent.getFirstName())
                .lastName(savedStudent.getLastName())
                .email(savedStudent.getEmail())
                .build();
    }

    public StudentDTO get(String id) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        return StudentDTO.builder()
                .id(studentEntity.getId())
                .firstName(studentEntity.getFirstName())
                .lastName(studentEntity.getLastName())
                .email(studentEntity.getEmail())
                .build();
    }

    public List<StudentDTO> getAll() {
        List<StudentEntity> studentEntities = studentRepository.findAll();
        return studentEntities.stream()
                .map(student -> StudentDTO.builder()
                        .id(student.getId())
                        .firstName(student.getFirstName())
                        .lastName(student.getLastName())
                        .email(student.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    public StudentDTO update(String id, StudentDTO studentDTO) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        studentEntity.setFirstName(studentDTO.getFirstName());
        studentEntity.setLastName(studentDTO.getLastName());
        studentEntity.setEmail(studentDTO.getEmail());

        StudentEntity updatedStudent = studentRepository.save(studentEntity);
        return StudentDTO.builder()
                .id(updatedStudent.getId())
                .firstName(updatedStudent.getFirstName())
                .lastName(updatedStudent.getLastName())
                .email(updatedStudent.getEmail())
                .build();
    }

    public void delete(String id) {
        studentRepository.deleteById(id);
    }
}
