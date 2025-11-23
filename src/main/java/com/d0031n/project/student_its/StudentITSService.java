package com.d0031n.project.student_its;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentITSService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentITSService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findStudentsByUsernames(List<String> usernames) {
        return studentRepository.findAllByUsernameIn(usernames);
    }

    public String getPersonNumberByUsername(String username) {
        Optional<Student> student = studentRepository.findByUsername(username);
        return student.map(Student::getPersonNumber).orElse("Student person number not found");
    }
}