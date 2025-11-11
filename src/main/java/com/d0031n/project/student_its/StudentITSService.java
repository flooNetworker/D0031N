package com.d0031n.project.student_its;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentITSService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentITSService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public String getStudentPersonNumberByUsername(String username) {
        Optional<Student> student = studentRepository.findByUsername(username);
        return student.map(Student::getPersonNumber).orElse("Student person number not found");
    }
}