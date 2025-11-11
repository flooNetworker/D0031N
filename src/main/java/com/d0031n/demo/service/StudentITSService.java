package com.d0031n.demo.service;

import com.d0031n.demo.model.Student;
import com.d0031n.demo.repository.StudentRepository;
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