package com.d0031n.project.service;

import com.d0031n.project.model.Student;
import com.d0031n.project.repository.StudentRepository;
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

    public String getStudentPersonNumber(String userName) {
        Optional<Student> student = studentRepository.findByUserName(userName);
        return student.map(Student::getPersonNumber).orElse("Student not found");
    }
}