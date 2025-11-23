package com.d0031n.project.student_its;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByUsername(String username);

    List<Student> findAllByUsernameIn(List<String> usernames);
}
